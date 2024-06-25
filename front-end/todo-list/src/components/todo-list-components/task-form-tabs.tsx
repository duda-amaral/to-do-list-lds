import { Tabs, TabsList, TabsTrigger, TabsContent } from "../ui/tabs";
import TaskDataForm from "./task-data-form";
import TaskForm from "./task-form";
import TaskPrazoForm from "./task-prazo-form";

export default function TaskFormTabs() {
  return (
    <>
      <Tabs defaultValue="account" className="w-[400px]">
        <TabsList>
          <TabsTrigger value="livre">Livre</TabsTrigger>
          <TabsTrigger value="data">Data</TabsTrigger>
          <TabsTrigger value="prazo">Prazo</TabsTrigger>
        </TabsList>
        <TabsContent value="livre">
          <TaskForm />
        </TabsContent>
        <TabsContent value="data">
          <TaskDataForm />
        </TabsContent>
        <TabsContent value="prazo">
          <TaskPrazoForm />
        </TabsContent>
      </Tabs>
    </>
  );
}
