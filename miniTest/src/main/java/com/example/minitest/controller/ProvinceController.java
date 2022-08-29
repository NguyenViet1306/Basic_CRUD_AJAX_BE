package com.example.minitest.controller;

import com.example.minitest.model.Country;
import com.example.minitest.model.Province;
import com.example.minitest.service.ICountryService;
import com.example.minitest.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.jar.Attributes;

@Controller
@RequestMapping("/province")
public class ProvinceController {

    @Value("${file-upload}")
    private String fileUpload;
    @Autowired    private ICountryService iCountryService;

    @Autowired
    private IProvinceService iProvinceService;

    @ModelAttribute("countries")
    public List<Country> countryList() {
        return iCountryService.findAll();
    }

//    @ModelAttribute("provinces")
//    public List<Province> provinceList() {
//        return iProvinceService.findAll();
//    }



// * Hiển thị theo dạng các page
    @ModelAttribute("provinces")
    public Page<Province> province() {
        return iProvinceService.findAll(Pageable.unpaged());
    }
    @GetMapping
    public ModelAndView findAllProvince(@PageableDefault(value = 4) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("display");
        Page<Province> provinces = iProvinceService.findAll(pageable);
        modelAndView.addObject("provinces", provinces);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createProvince() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("province", new Province());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("province") Optional<Province> province,
                         RedirectAttributes redirectAttributes) {
        if (province.isPresent()) {
            MultipartFile multipartFile = province.get().getImage();
            String imageUrl = multipartFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload
                        + multipartFile.getOriginalFilename()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            province.get().setImageUrl(imageUrl);
            iProvinceService.save(province.get());}
        else {
            redirectAttributes.addFlashAttribute("mess", "Tạo không thành công");
        }
        redirectAttributes.addFlashAttribute("mess", "Tạo thành công");
        return "redirect:/province";
    }

    @GetMapping("/detail/update/{id}")
    public ModelAndView updateProvince(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("update");
        Optional<Province> province = iProvinceService.findById(id);
        if (province.isPresent()) {
            modelAndView.addObject("province", province.get());
        }
        return modelAndView;
    }

    @PostMapping("/detail/update/{id}")
    public String update(@ModelAttribute("province") Optional<Province> province,
                         RedirectAttributes redirectAttributes) {
        if (province.isPresent()) {
            MultipartFile multipartFile = province.get().getImage();
            String imageUrl = multipartFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload + multipartFile.getOriginalFilename()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            province.get().setImageUrl(imageUrl);
            iProvinceService.save(province.get());
        } else {
            redirectAttributes.addFlashAttribute("mess", "Cập nhập ko thành công");
        }
        return "redirect:/province";
    }

    @GetMapping("/detail/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes) {
        iProvinceService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xóa thành công");
        return "redirect:/province";
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detailProvince(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("detail");
        Optional<Province> province = iProvinceService.findById(id);
        modelAndView.addObject("province", province.get());
        return modelAndView;
    }

    @PostMapping("/search")
    public ModelAndView search( @RequestParam("search") Optional<String> name_province, Pageable pageable
                               ) {
        ModelAndView modelAndView = new ModelAndView("display");
        if (name_province.isPresent()) {
            Page<Province> provinces =  iProvinceService.findAllByNameContaining(name_province.get(), pageable);
            modelAndView.addObject("provinces", provinces);
        }
        return modelAndView;
    }
}
