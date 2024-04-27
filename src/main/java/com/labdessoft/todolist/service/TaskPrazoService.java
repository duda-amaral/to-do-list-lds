package com.labdessoft.todolist.service;

import com.labdessoft.todolist.entity.TaskPrazo;
import com.labdessoft.todolist.repository.TaskPrazoRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.labdessoft.todolist.enums.TaskTipo.PRAZO;

@Service
public class TaskPrazoService {

    @Autowired
    private TaskPrazoRepository taskPrazoRepository;

    @Operation(description = "Pesquisa uma tarefa do tipo prazo pelo id")
    public TaskPrazo findById(Long id)  {
        return this.taskPrazoRepository.findById(id).orElseThrow(() -> new RuntimeException(
                "Tarefa não encontrada! Id: " + id + ", Tipo: " + TaskPrazo.class.getName()));
    }

    @Operation(description = "lista todas as tarefas do tipo prazo cadastradas")
    public List<TaskPrazo> listAll() {
        return this.taskPrazoRepository.findAll();
    }
    @Operation(description = "cadastra tarefas")
    @Transactional
    public TaskPrazo create(TaskPrazo obj) {
        obj.setId(null);
        obj.setCompleted(false);
        obj.setType(PRAZO);
        obj.setCreationDate(LocalDate.now());

        obj = this.taskPrazoRepository.save(obj);

        String status = getStatus(obj.getId());
        obj.setStatus(status);
        return obj;
    }

    @Operation(description = "atualiza a descrição de uma tarefa do tipo prazo especificada pelo id")
    public TaskPrazo update(TaskPrazo obj)  {
        TaskPrazo newObj = findById(obj.getId());
        BeanUtils.copyProperties(obj, newObj, "id", "completed", "type", "creationDate");
        String status = getStatus(obj.getId());
        newObj.setStatus(status);
        return this.taskPrazoRepository.save(newObj);
    }

    @Operation(description = "atualiza o status de uma tarefa do tipo prazo")
    public TaskPrazo updateStatus(TaskPrazo obj)  {
        TaskPrazo newObj = findById(obj.getId());
        BeanUtils.copyProperties(obj, newObj, "id", "description", "dueDate", "status", "type", "creationDate");
        String status = getStatus(obj.getId());
        newObj.setStatus(status);
        return this.taskPrazoRepository.save(newObj);
    }

    @Operation(description = "deleta uma tarefa do tipo prazo especificada pelo id")
    public void delete(Long id) {
        TaskPrazo task = findById(id);
        this.taskPrazoRepository.delete(task);

    }

    @Operation(description = "Retorna o status de uma tarefa do tipo prazo especificada pelo id")
    public String getStatus(Long id) {
        TaskPrazo task = findById(id);

        Integer dueDays = task.getDueDays();
        LocalDate currentDate = LocalDate.now();

        if (task.getCompleted() == true) {
            return "Concluída";
        } else if (currentDate.isAfter(task.getCreationDate().plusDays(dueDays))) {
            long daysOverdue = ChronoUnit.DAYS.between(task.getCreationDate().plusDays(dueDays), currentDate);
            return daysOverdue + " dias de atraso";
        } else {
            return "Prevista: " + dueDays + " dias";
        }
    }
}
