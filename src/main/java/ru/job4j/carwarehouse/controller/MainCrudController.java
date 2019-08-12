package ru.job4j.carwarehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.job4j.carwarehouse.models.Car;
import ru.job4j.carwarehouse.models.Picture;
import ru.job4j.carwarehouse.service.CarServiceInterface;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @version 1.0
 * @since 13.08.2019
 */

@Controller
public class MainCrudController {

    private static final Logger LOGGER = Logger.getLogger(MainCrudController.class);

    @Autowired
    private CarServiceInterface carService;

    private int carId = 0;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listCars(Model model) {
        List<Car> list = carService.getAllCar();
        List<Picture> pictures;
        for (Car car : list) {
            pictures  = carService.getPicturesByCar(car);
            car.setPictures(pictures);
        }
        LOGGER.info(list);
        model.addAttribute("cars", list);
        return "Cars";
    }

    @RequestMapping(value = "/addCarForm", method = RequestMethod.GET)
    public String addCarForm(Model model) {
        Car car = new Car();
        model.addAttribute("newCar", car);
        return "AddCar";
    }

    @RequestMapping(value = "/addCar", method = RequestMethod.POST)
    public String addCar(@ModelAttribute("newCar") Car car, Model model) {
        car = carService.constructCar(
                car,
                car.getBodytype(),
                car.getEngine(),
                car.getTransmission()
        );
        car.setCreated(new java.sql.Date(System.currentTimeMillis()));
        int result = carService.addCar(car);
        if (result > 0) {
            return "redirect:/";
        } else {
            model.addAttribute("error", "Cannot be added");
            return "AddCar";
        }
    }

    @RequestMapping("/getBodytype")
    public void getBodyTypes(HttpServletResponse resp) throws IOException {
        List<String> bodyNames = carService.getBodyTypes();
        ObjectMapper objectMapper = new ObjectMapper();
        String toJson = objectMapper.writeValueAsString(bodyNames);
        resp.setContentType("text/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        LOGGER.info(toJson);
        writer.append(toJson);
        writer.flush();
    }

    @RequestMapping("/getEngine")
    public void getEngineType(HttpServletResponse resp) throws IOException {
        List<String> engineNames = carService.getEngineTypes();
        ObjectMapper objectMapper = new ObjectMapper();
        String toJson = objectMapper.writeValueAsString(engineNames);
        resp.setContentType("text/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        LOGGER.info(toJson);
        writer.append(toJson);
        writer.flush();

    }

    @RequestMapping("/getTransmission")
    public void getTransmission(HttpServletResponse resp) throws IOException {
        List<String> transNames = carService.getTransmissionTypes();
        ObjectMapper objectMapper = new ObjectMapper();
        String toJson = objectMapper.writeValueAsString(transNames);
        resp.setContentType("text/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        LOGGER.info(toJson);
        writer.append(toJson);
        writer.flush();
    }

    @RequestMapping("/getLocation")
    public void getLocation(HttpServletResponse resp) throws IOException {
        List<String> locations = carService.getLocation();
        ObjectMapper objectMapper = new ObjectMapper();
        String toJson = objectMapper.writeValueAsString(locations);
        resp.setContentType("text/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        LOGGER.info(toJson);
        writer.append(toJson);
        writer.flush();
    }

    @RequestMapping(value = "/updateDelete", method = RequestMethod.GET)
    public void updateSoldStatus(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("carId"));
        boolean done = Boolean.valueOf(req.getParameter("sold"));
        carService.statusChange(id, done);
    }

    @RequestMapping(value = "/updateDelete", method = RequestMethod.POST)
    public String deleteCar(HttpServletRequest req) {
        int id = Integer.valueOf(req.getParameter("carId"));
        Car car = carService.getCarById(id);
        carService.deleteCar(car);
        return "redirect:/";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public String contentFilter(HttpServletRequest req, Model model) {
        String soldStatus = req.getParameter("filterSold");
        String withPicture = req.getParameter("filterImage");
        String dateTime = req.getParameter("filterDate");
        String name = req.getParameter("filterName");
        Date date = carService.getCarById(1).getCreated();
        List<Car> list;
        List<Picture> pictures;
        List<Car> result = new ArrayList<>();
        if (dateTime != null) {
            date = new Date(System.currentTimeMillis());
        }
        if (soldStatus != null && !name.equals("")) {
            list = carService.filterCarsBySoldAndBrand(false, name, date);
        } else if (soldStatus != null) {
            list = carService.filterCarsBySold(false, date);
        } else if (!name.equals("")) {
            list = carService.filterCarsByBrand(name, date);
        } else {
            list = carService.getAllCarsWithData(date);
        }

        for (Car car : list) {
            pictures  = carService.getPicturesByCar(car);
            car.setPictures(pictures);
        }

        if (withPicture != null) {
            for (Car c : list) {
                if (!c.getPictures().isEmpty()) {
                    result.add(c);
                }
            }
            list = result;
        }
        model.addAttribute("cars", list);
        return "CarFilter";
    }

    @RequestMapping(value = "/imageUpload", method = RequestMethod.GET)
    public String uploadImage(HttpServletRequest req, Model model) {
        carId = Integer.valueOf(req.getParameter("carIdForImage"));
        return "ImageUpload";
    }

    @RequestMapping(value = "/imageUpload", method = RequestMethod.POST)
    public String addImage(HttpServletRequest req, Model model) throws IOException {
        Car car = carService.getCarById(carId);
        Picture picture;
        int result = 0;
        if (ServletFileUpload.isMultipartContent(req)) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                List<FileItem> items = upload.parseRequest(req);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (!item.isFormField()) {
                        byte[] bytes = IOUtils.toByteArray(item.getInputStream());
                        picture = new Picture(bytes);
                        picture.setCar(car);
                        result = carService.addPicture(picture);
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }
        if (result > 0) {
            return "redirect:/";
        } else {
            model.addAttribute("error", "Cannot be uploaded");
            return "ImageUpload";
        }
    }
}