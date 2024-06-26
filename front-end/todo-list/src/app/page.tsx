import TaskFormTabs from "@/components/todo-list-components/task-form-tabs";
import TasksTable from "@/components/todo-list-components/tasks-table";

export default function Home() {
  return (
    <div className="bg-slate-300 min-h-screen flex flex-col items-center justify-between p-24">
      <div className="grid grid-cols-2 gap-4 w-full">
        <TaskFormTabs />
        <TasksTable />
      </div>
    </div>
  );
}
