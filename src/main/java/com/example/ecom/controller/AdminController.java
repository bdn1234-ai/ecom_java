package com.example.ecom.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.example.ecom.model.Category;
import com.example.ecom.model.Product;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecom.service.CategoryService;
import com.example.ecom.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping()
    public String index() {
        return "admin/index";
    }

    @GetMapping("/loadAddProduct")
    public String loadAddProduct(Model m) {
        List<Category> categories = categoryService.getAllCategory();
        m.addAttribute("categories", categories);
        return "admin/loadAddProduct";
    }

    @GetMapping("/category")
    public String category(Model m, @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        m.addAttribute("categorys", categoryService.getAllCategory());
        Page<Category> page = categoryService.getAllCategorPagination(pageNo, pageSize);
        List<Category> categorys = page.getContent();
        m.addAttribute("categorys", categorys);

        m.addAttribute("pageNo", page.getNumber());
        m.addAttribute("pageSize", pageSize);
        m.addAttribute("totalElements", page.getTotalElements());
        m.addAttribute("totalPages", page.getTotalPages());
        m.addAttribute("isFirst", page.isFirst());
        m.addAttribute("isLast", page.isLast());

        return "admin/category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
            HttpSession session) {
            try{
                categoryService.createCategory(category, file);
                session.setAttribute("successMsg", "Category Added Successfully ! ");
            }catch(Exception e){
                session.setAttribute("errorMsg", "Error saving file: " + e.getMessage());
            }
        return "redirect:/admin/category";
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable int id, HttpSession session) {
        Boolean deteleCategory = categoryService.deleteCategory(id);
        if (deteleCategory) {
            session.setAttribute("successMsg", "Đã xóa sản phẩm thành công");
        }
        else {
            session.setAttribute("errorMsg", "Đã có lỗi xảy ra trong quá trình xóa sản phẩm");
        }
        return "redirect:/admin/category";
    }


    @GetMapping("/loadEditCategory/{id}")
    public String loadEditCategory(@PathVariable int id, Model m) {
        m.addAttribute("category", categoryService.getCategoryById(id));
        return "admin/editCategory";
    }
    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
        HttpSession session) throws IOException{
        Category oldCategory = categoryService.getCategoryById(category.getId());
        String imageName = file.isEmpty() ? oldCategory.getImageName() : file.getOriginalFilename();

        if (!ObjectUtils.isEmpty(category)) {
            oldCategory.setName(category.getName());
            oldCategory.setIsActive(category.getIsActive());
            oldCategory.setImageName(imageName);

        }
        Category updateCategory = categoryService.saveCategory(oldCategory);

        if(!ObjectUtils.isEmpty(updateCategory)){
            if(!file.isEmpty()){
                File saveFile = new ClassPathResource("static/img").getFile();

                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
                        + file.getOriginalFilename());

                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            }

            
            session.setAttribute("succMsg","Cập nhật sản phẩm thành công");
        }else{
            session.setAttribute("errorMsg","Đã có lỗi xảy ra trong quá trình xóa sản phẩm");
        }
    
        return "redirect:/admin/loadEditCategory/" + category.getId();
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image,
            HttpSession session) throws IOException {

        // 1. CHUẨN BỊ DATA & LƯU DB LẦN 1 (Giống flow saveCategory)
        // Chuẩn bị imageName và gán vào model trước khi lưu lần 1
        String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename(); 
        product.setImage(imageName);
        
        

        // Kiểm tra tồn tại sản phẩm (Đã sửa lỗi cú pháp)
        Boolean existProduct = productService.existProduct(product.getTitle()); 

        if (existProduct) {
            session.setAttribute("errorMsg", "Tên sản phẩm đã tồn tại");
        } else {

            Product saveProduct = productService.saveProduct(product); // LƯU DB LẦN 1

            if (ObjectUtils.isEmpty(saveProduct)) {
                session.setAttribute("errorMsg", "Không lưu được ! Lỗi Servel nội bộ");

            } else {

                try {
                    // 2. LƯU FILE VẬT LÝ & LƯU DB LẦN 2 (Giống flow saveCategory)
                    if (!image.isEmpty()) {
                        // KHẮC PHỤC LỖI: Sử dụng đường dẫn vật lý ổn định
                        String uploadDir = "uploads/img/product_img/";
                        File directory = new File(uploadDir);
                        if (!directory.exists()) {
                            directory.mkdirs();
                        }

                        Path path = Paths.get(uploadDir + image.getOriginalFilename());
                        Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    }

                    // Cập nhật lại tên ảnh và lưu DB lần 2
                    product.setImage(image.getOriginalFilename());
                    productService.saveProduct(product); // LƯU DB LẦN 2

                    session.setAttribute("succMsg", "Lưu sản phẩm thành công");

                } catch (IOException e) {
                    e.printStackTrace();
                    session.setAttribute("errorMsg", "Lỗi I/O khi lưu tệp ảnh: " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute("errorMsg", "Lỗi không mong muốn: " + e.getMessage());
                }
            }
        }

        return "redirect:/admin/loadAddProduct";
    }
}