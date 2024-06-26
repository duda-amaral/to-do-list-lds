import { createTask, createTaskData, createTaskPrazo } from '@/api/fetchTasks';

export const handleSubmitTask = async (
  e: React.FormEvent<HTMLFormElement>,
  description: string,
  priority: string
) => {
  e.preventDefault();
  try {
    await createTask(description, priority);
    alert("Tarefa criada com sucesso!");
  } catch (error) {
    console.error(error);
    alert("Erro ao criar tarefa");
  }
};

export const handleSubmitTaskData = async (
  e: React.FormEvent<HTMLFormElement>,
  description: string,
  priority: string,
  dueDate: Date | null
) => {
  e.preventDefault();
  try {
    await createTaskData(description, priority, dueDate);
    alert("Tarefa criada com sucesso!");
  } catch (error) {
    console.error(error);
    alert("Erro ao criar tarefa");
  }
};

export const handleSubmitTaskPrazo = async (
  e: React.FormEvent<HTMLFormElement>,
  description: string,
  priority: string,
  dueDays: number | null
) => {
  e.preventDefault();
  try {
    await createTaskPrazo(description, priority, dueDays);
    alert("Tarefa criada com sucesso!");
  } catch (error) {
    console.error(error);
    alert("Erro ao criar tarefa");
  }
};