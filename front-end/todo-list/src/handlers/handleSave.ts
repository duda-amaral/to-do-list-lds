import { fetchTasks, updateTask } from '@/api/fetchTasks';
import { Task } from '@/types';

export const handleSave = async (taskId: number, tasks: Task[], newDescription: string, newPriority: any, newType: any) => {
  try {
    const taskToUpdate = tasks.find((task) => task.id === taskId);
    const updatedTask = {
      ...taskToUpdate,
      description: newDescription,
      priority: newPriority,
      type: newType,
    };
    await updateTask(taskId, updatedTask);
    await fetchTasks();
  } catch (error) {
    console.error("Error updating task:", error);
  }
};
