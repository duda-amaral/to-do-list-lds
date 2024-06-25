import { fetchTasks, completeTask } from '@/api/fetchTasks';
import { Task} from '@/types';

export const handleComplete = async (taskId: number, completed: boolean, setTasks: (tasks: Task[]) => void) => {
  try {
    await completeTask(taskId, completed);
    const updatedTasks = await fetchTasks();
    setTasks(updatedTasks); // Refresh task list after successful completion
  } catch (error) {
    console.error("Error completing task:", error);
  }
};
