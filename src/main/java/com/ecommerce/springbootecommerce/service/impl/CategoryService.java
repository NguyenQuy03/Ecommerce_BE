package com.ecommerce.springbootecommerce.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.ecommerce.springbootecommerce.dto.BaseDTO;
import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.entity.CategoryEntity;
import com.ecommerce.springbootecommerce.exception.CustomException;
import com.ecommerce.springbootecommerce.repository.CategoryRepository;
import com.ecommerce.springbootecommerce.service.ICategoryService;
import com.ecommerce.springbootecommerce.util.ServiceUtil;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ServiceUtil serviceUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public void save(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity;
        Optional<CategoryEntity> existedEntity = categoryRepo.findOneByCode(categoryDTO.getCode());

        if (categoryDTO.getId() != null) {
            CategoryEntity preCategoryEntity = categoryRepo.findById(categoryDTO.getId()).get();

            if (!categoryDTO.getCode().equals(preCategoryEntity.getCode()) && existedEntity.isPresent()) {
                throw new CustomException("Category has already exist", HttpStatus.CONFLICT);
            }

            if (!categoryDTO.getThumbnail().equals(preCategoryEntity.getThumbnail())) {
                categoryDTO.setThumbnail(updateThumnail(categoryDTO));
            }
            modelMapper.map(categoryDTO, preCategoryEntity);
            categoryEntity = modelMapper.map(preCategoryEntity, CategoryEntity.class);
        } else {
            if (existedEntity.isPresent()) {
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
    public void delete(Long id) {
        categoryRepo.deleteById(id);
    }

    @Override
    public CategoryDTO findOneByCode(String code) {
        return categoryRepo.findOneByCode(code).map(item -> modelMapper.map(item, CategoryDTO.class)).orElse(null);
    }

    @Override
    public CategoryDTO findOneByIdAndAccountId(long id, long accountId) {
        return categoryRepo.findOneByIdAndAccountId(id, accountId).map(item -> modelMapper.map(item, CategoryDTO.class))
                .orElse(null);
    }

    public List<CategoryDTO> findAll() {
        List<CategoryEntity> listCategoriesEntity = categoryRepo.findAll();
        return toListCategoryDTO(listCategoriesEntity);
    }

    @Override
    public BaseDTO<CategoryDTO> findAllByAccountId(long accountId, Pageable pageable) {
        Page<CategoryEntity> pageEntity = categoryRepo.findAllByAccountId(accountId, pageable);
        BaseDTO<CategoryDTO> res = serviceUtil.mapDataFromPage(pageEntity);

        List<CategoryEntity> listCategoryEntity = pageEntity.getContent();
        List<CategoryDTO> listCategoryDTO = listCategoryEntity.stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDTO.class))
                .collect(Collectors.toList());
        res.setListResult(listCategoryDTO);
        return res;
    }

    @Override
    public List<CategoryDTO> findAllByAccountId(long accountId) {
        List<CategoryEntity> listCategoryEntity = categoryRepo.findAllByAccountId(accountId);

        List<CategoryDTO> listCategoryDTO = listCategoryEntity.stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDTO.class))
                .collect(Collectors.toList());
        return listCategoryDTO;
    }

    // REUSE
    private List<CategoryDTO> toListCategoryDTO(List<CategoryEntity> listCategoryEntity) {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (CategoryEntity entity : listCategoryEntity) {
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
                                    "folder", "Category_thumbnail"))
                    .get("url")
                    .toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return thumbnailUrl;
    }
}
