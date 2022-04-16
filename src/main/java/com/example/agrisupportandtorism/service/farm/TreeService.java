package com.example.agrisupportandtorism.service.farm;

import com.example.agrisupportandtorism.entity.farm.Tree;
import com.example.agrisupportandtorism.exception.ResourceNotFoundException;
import com.example.agrisupportandtorism.repository.farm.TreeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TreeService {
    @Autowired
    private TreeRepo treeRepo;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public List<Tree> findAll() {
        return treeRepo.findAll();
    }

    public List<Tree> findByName(String name) {
        return treeRepo.findByNameContaining(name);
    }

    public Tree findById(Integer id) {
        Optional<Tree> treeOpt = treeRepo.findById(id);
        if (treeOpt.isPresent()) {
            return treeOpt.get();
        } else {
            throw new ResourceNotFoundException(String.format("Cây không tồn tại, mã cây= [%s]", id));
        }
    }

    @Transactional
    public Tree insertTree(Tree tree) {
        Tree result = treeRepo.save(tree);
        logger.info(String.format("Add tree, id=[%s]", result.getId()));
        return result;
    }
}
