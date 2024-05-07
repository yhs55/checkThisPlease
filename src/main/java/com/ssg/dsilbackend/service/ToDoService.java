//package com.ssg.dsilbackend.service;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ToDoService {
//    private ToDoRepository toDoRepository;
//
//    public ToDoService (ToDoRepository toDoRepository) {
//        this.toDoRepository = toDoRepository;
//    }
//
//    // 새로만들어서 저장
//    public ToDoDto createTodo(CreateToDoDto createToDoDTO) {
//        ToDo newToDo = new ToDo();
//        newToDo.setName(createToDoDTO.getName());
//        newToDo.setCompleted(createToDoDTO.getCompleted());
//        ToDo toDo = toDoRepository.save(newToDo);
//        return new ToDoDto(toDo);
//    }
//
//    public List<ToDoDto> getToDos() {
//        List<ToDo> toDos = toDoRepository.findAll();
//        return toDos.stream().map(entity -> new ToDoDto(entity)).toList();
//    }
//
//    public List<ToDoDto> getToDos(Boolean completed) {
//        List<ToDo> toDos = toDoRepository.findByCompleted(completed);
//        return toDos.stream().map(entity -> new ToDoDto(entity)).toList();
//    }
//
//    public ToDoDto getToDoById(Long id) {
//        Optional<ToDo> toDo = toDoRepository.findById(id);
//        if (toDo.isPresent()) {
//            return new ToDoDto(toDo.get());
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "getToDoById - to do not found");
//        }
//    }
//
//    public ToDoDto updateToDo(Long id, UpdateToDoDto updateToDoDto) {
//        Optional<ToDo> toDo = toDoRepository.findById(id);
//        if (toDo.isPresent()) {
//            toDo.get().setName(updateToDoDto.getName());
//            toDo.get().setCompleted(updateToDoDto.getCompleted());
//            toDoRepository.save(toDo.get());
//            return new ToDoDto(toDo.get());
//        } else {
//            throw new ToDoException(404, "updateToDo - to do not found");
//        }
//    }
//
//    public void deleteToDo(Long id) {
//        Optional<ToDo> toDo = toDoRepository.findById(id);
//        if (toDo.isPresent()) {
//            toDoRepository.delete(toDo.get());
//        } else {
//            throw new RuntimeException("deleteToDo - to do not found");
//        }
//    }
//}
