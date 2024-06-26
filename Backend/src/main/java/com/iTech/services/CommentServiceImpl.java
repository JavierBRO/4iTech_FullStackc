package com.iTech.services;

import com.iTech.models.Comment;
import com.iTech.repository.CommentRepository;

import com.iTech.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {

        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> findCommentsOrderByDateTimeDesc() {
        return commentRepository.findAllByOrderByDateTimeDesc();
    }

    @Override
    public List<Comment> findByKeynote_Id(Long keynoteId) {
        return this.commentRepository.findByKeynote_IdOrderByDateTimeDesc(keynoteId);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public Comment createComment(Comment comment) {
        Logger logger = LoggerFactory.getLogger(Comment.class);
        try {
            SecurityUtils.getCurrentUser().ifPresent(user -> comment.setUser(user));
            return commentRepository.save(comment);
        } catch (Exception e) {
            // Registrar el error
            logger.error("Hubo un error al crear el comment", e);

            // Lanzar una nueva excepción
            throw new RuntimeException("Hubo un error al crear el comment: " + e.getMessage(), e);
        }

    }

    @Override
    public Comment updateComment(Long id, Comment comment) {

        Optional<Comment> commentOptional = commentRepository.findById(id);

        if (commentOptional.isPresent()){

            commentOptional.get().setRating(comment.getRating());
            commentOptional.get().setOpinion(comment.getOpinion());
            commentOptional.get().setDateTime(comment.getDateTime());
            //commentOptional.get().setUser(comment.getUser());
            commentOptional.get().setKeynote(comment.getKeynote());
            //esto es actualizar los atributos que se consideren necesarios, se pueden omitir, estan todos.

            SecurityUtils.getCurrentUser().ifPresent(user -> comment.setUser(user));
            return commentRepository.save(commentOptional.get());
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteComment(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()){
            commentRepository.deleteById(id);
            return true;
        } else { return false; }
    }
}