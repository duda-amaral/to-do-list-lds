import * as React from "react";
import { CalendarIcon } from "@radix-ui/react-icons";
import { addDays, format } from "date-fns";

import { cn } from "@/lib/utils";
import { Button } from "@/components/ui/button";
import { Calendar } from "@/components/ui/calendar";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

interface DatePickerWithPresetsProps {
  onChange: (date: Date | null) => void;
}

export const DatePickerWithPresets: React.FC<DatePickerWithPresetsProps> = ({
  onChange,
}) => {
  const [date, setDate] = React.useState<Date>();

  const handleSelectDate = (selectedDate: Date | undefined) => {
    setDate(selectedDate);
    onChange(selectedDate || null);
  };

  const handlePresetChange = (value: string) => {
    const daysToAdd = parseInt(value);
    if (!isNaN(daysToAdd)) {
      setDate(addDays(new Date(), daysToAdd));
      onChange(addDays(new Date(), daysToAdd));
    }
  };

  return (
    <Popover>
      <PopoverTrigger asChild>
        <Button
          variant={"outline"}
          className={cn(
            "w-[240px] justify-start text-left font-normal",
            !date && "text-muted-foreground"
          )}
        >
          <CalendarIcon className="mr-2 h-4 w-4" />
          {date ? format(date, "PPP") : <span>Pick a date</span>}
        </Button>
      </PopoverTrigger>
      <PopoverContent align="start" className="flex w-auto flex-col space-y-2 p-2">
        <Select onValueChange={handlePresetChange}>
          <SelectTrigger>
            <SelectValue placeholder="Select" />
          </SelectTrigger>
          <SelectContent position="popper">
            <SelectItem value="0">Today</SelectItem>
            <SelectItem value="1">Tomorrow</SelectItem>
            <SelectItem value="3">In 3 days</SelectItem>
            <SelectItem value="7">In a week</SelectItem>
          </SelectContent>
        </Select>
        <div className="rounded-md border">
          <Calendar mode="single" selected={date} onSelect={handleSelectDate} />
        </div>
      </PopoverContent>
    </Popover>
  );
};
