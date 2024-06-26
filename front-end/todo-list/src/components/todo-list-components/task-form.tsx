'use client'
import React, { useState } from "react";
import { Label } from "../ui/label";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from "../ui/select";
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import {
  Card,
  CardHeader,
  CardTitle,
  CardDescription,
  CardContent,
  CardFooter,
} from "../ui/card";
import { handleSubmitTask } from "@/handlers/handleSubmit";

const TaskForm = () => {
  const [description, setDescription] = useState("");
  const [priority, setPriority] = useState("");

  return (
    <Card className="w-[350px] text-black bg-white">
      <CardHeader>
        <CardTitle>Criar tarefa</CardTitle>
        <CardDescription>Crie sua tarefa do tipo Livre.</CardDescription>
      </CardHeader>
      <CardContent>
        <form onSubmit={(e) => handleSubmitTask(e, description, priority)}>
          <div className="grid w-full items-center gap-4">
            <div className="flex flex-col space-y-1.5">
              <Label htmlFor="description">Descrição</Label>
              <Input id="description" value={description}  onChange={(e) => setDescription(e.target.value)} type="text" required min="10" placeholder="descrição da tarefa" />
            </div>
            <div className="flex flex-col space-y-1.5">
              <Label htmlFor="priority">Prioridade</Label>
              <Select onValueChange={(value) => setPriority(value)}>
                <SelectTrigger id="priority">
                  <SelectValue placeholder="Select" />
                </SelectTrigger>
                <SelectContent position="popper">
                  <SelectItem value="ALTA">Alta</SelectItem>
                  <SelectItem value="MÉDIA">Média</SelectItem>
                  <SelectItem value="BAIXA">Baixa</SelectItem>
                </SelectContent>
              </Select>
            </div>
          </div>
        </form>
      </CardContent>
      <CardFooter className="flex justify-between">
        <Button variant="destructive" type="button">Cancel</Button>
        <Button variant="outline" type="submit">Salvar</Button>
      </CardFooter>
    </Card>
  );
};

export default TaskForm;
