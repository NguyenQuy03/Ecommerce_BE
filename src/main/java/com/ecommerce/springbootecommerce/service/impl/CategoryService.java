package com.ecommerce.springbootecommerce.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.entity.CategoryEntity;
import com.ecommerce.springbootecommerce.repository.CategoryRepository;
import com.ecommerce.springbootecommerce.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {
    
    @Autowired
    private CategoryRepository categoryRepo;
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public void save(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity;

        if (categoryDTO.getId() != null) {
            CategoryEntity preCategoryEntity = categoryRepo.findById(categoryDTO.getId()).get();
            if(categoryDTO.getThumbnail() != preCategoryEntity.getThumbnail()) {
                categoryDTO.setThumbnail(updateThumnail(categoryDTO));
            }
            modelMapper.map(categoryDTO, preCategoryEntity);
            categoryEntity = modelMapper.map(preCategoryEntity, CategoryEntity.class);
        } else {
            Optional<CategoryEntity> entity = categoryRepo.findOneByCode(categoryDTO.getCode());
            if(entity.isPresent()) {
                throw new RuntimeException("Category has already exist");
            }
            categoryEntity = modelMapper.map(categoryDTO, CategoryEntity.class);
            categoryEntity.setThumbnail(updateThumnail(categoryDTO));
        }

        categoryRepo.save(categoryEntity);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            categoryRepo.deleteById(id);
        }   
    }

    @Override
    public long count() {
        return categoryRepo.count();
    }

    public List<CategoryDTO> findAll() {
        List<CategoryEntity> listCategoriesEntity = categoryRepo.findAll();
        return toListCategoryDTO(listCategoriesEntity);
    }
    
    @Override
    public List<CategoryDTO> findAll(Pageable pageable) {
        List<CategoryEntity> listCategoryEntity = categoryRepo.findAll(pageable).getContent();
        return toListCategoryDTO(listCategoryEntity);
    }

    // REUSE
    private List<CategoryDTO> toListCategoryDTO(List<CategoryEntity> listCategoryEntity) {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for(CategoryEntity entity : listCategoryEntity) {
            categoryDTOS.add(modelMapper.map(entity, CategoryDTO.class));
        }
        return categoryDTOS;
    }

    private String updateThumnail(CategoryDTO categoryDTO) {
        String thumbnailUrl = "";
        try {
            thumbnailUrl = cloudinary.uploader()
                    .upload(
                        categoryDTO.getThumbnail(), 
                        Map.of(
                            "public_id", UUID.randomUUID().toString(),
                            "folder", "Category_thumbnail"
                        )
                    )
                    .get("url")
                    .toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return thumbnailUrl;
    }

    @Override
    public CategoryDTO findOneByCode(String code) {
        CategoryEntity entity = categoryRepo.findOneByCode(code).get();
        return modelMapper.map(entity, CategoryDTO.class);
    }
}
