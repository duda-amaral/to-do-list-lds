import { fetchTasks, deleteTask } from '@/api/fetchTasks';
import { Task} from '@/types';

export const handleDelete = async (taskId: number, setTasks: (tasks: Task[]) => void) => {
  try {
    await deleteTask(taskId);
    const updatedTasks = await fetchTasks();
    setTasks(updatedTasks);
  } catch (error) {
    console.error("Error deleting task:", error);
  }
};
