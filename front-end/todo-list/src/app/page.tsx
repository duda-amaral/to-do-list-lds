import TaskFormTabs from "@/components/todo-list-components/task-form-tabs";
import TasksTable from "@/components/todo-list-components/tasks-table";

export default function Home() {
  return (
    <>
      <main className="flex min-h-screen flex-col items-center justify-between p-24 bg-slate-300">
        <div className="grid grid-cols-2 gap-2 w-full">
          <TaskFormTabs />
          <TasksTable />
        </div>
      </main>
    </>
  );
}
