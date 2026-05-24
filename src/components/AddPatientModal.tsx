import { useEffect, useState } from "react";

import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

import { Checkbox } from "@/components/ui/checkbox";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

interface Department {
  id: number;
  name: string;
}

interface AddPatientModalProps {
  onClose: () => void;
  onSubmit: (data: any) => void;
  departments: Department[];
}

interface FormErrors {
  name?: string;
  age?: string;
  gender?: string;
  complaint?: string;
  departmentId?: string;

  breathing?: string;
  consciousness?: string;
}

const inputClass =
  "w-full bg-white/5 border border-white/10 rounded-xl h-12 text-white px-4 focus-visible:ring-2 focus-visible:ring-blue-500";

const selectContentClass =
  "bg-[#0f172a] border border-white/10 text-white";

const selectItemClass =
  "focus:bg-white/10 focus:text-white";

export default function AddPatientModal({
  onClose,
  onSubmit,
  departments,
}: AddPatientModalProps) {

  const [form, setForm] = useState({
    name: "",
    age: "",
    gender: "",
    complaint: "",
    painLevel: 5,
    breathing: "",
    consciousness: "",
    departmentId: 0,

    assignedDoctor: "",
    medicalCondition: "",
    critical: false,

    emergencyOverride: false,
  });

  const [errors, setErrors] =
    useState<FormErrors>({});

  useEffect(() => {

    document.body.style.overflow =
      "hidden";

    return () => {
      document.body.style.overflow =
        "auto";
    };

  }, []);

  const validate = () => {

    const newErrors: FormErrors = {};

    if (!form.name.trim()) {

      newErrors.name =
        "Patient name is required";

    }

    if (!form.age) {

      newErrors.age =
        "Age is required";

    }

    if (!form.gender) {

      newErrors.gender =
        "Gender is required";

    }

    if (!form.complaint) {

      newErrors.complaint =
        "Complaint is required";

    }

    if (!form.departmentId) {

      newErrors.departmentId =
        "Department is required";

    }

    if (!form.breathing) {

      newErrors.breathing =
        "Breathing status is required";

    }

    if (!form.consciousness) {

      newErrors.consciousness =
        "Consciousness status is required";

    }

    setErrors(newErrors);

    return (
      Object.keys(newErrors).length === 0
    );
  };

  const handleSubmit = () => {

    if (!validate()) return;

    onSubmit(form);

  };

  return (

    <div className="fixed inset-0 z-50 bg-black/70 backdrop-blur-sm flex items-center justify-center p-4 overflow-y-auto">

      <div className="w-full max-w-3xl rounded-3xl bg-[#0f172a] border border-white/10 p-8 shadow-2xl">

        {/* HEADER */}
        <div className="flex items-start justify-between">

          <div>

            <h2 className="text-3xl font-bold text-white">
              Add New Patient
            </h2>

            <p className="text-slate-400 mt-1">
              Emergency intake form
            </p>

          </div>

          <button
            onClick={onClose}
            className="text-slate-400 hover:text-white text-xl transition"
          >
            ✕
          </button>

        </div>

        {/* FORM */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-5 mt-8">

          {/* NAME */}
          <div className="space-y-2">

            <Label>
              Patient Name
            </Label>

            <Input
              className={inputClass}
              value={form.name}
              onChange={(e) =>
                setForm({
                  ...form,
                  name: e.target.value,
                })
              }
              placeholder="Enter patient name"
            />

            {errors.name && (

              <p className="text-red-400 text-sm">
                {errors.name}
              </p>

            )}

          </div>

          {/* AGE */}
          <div className="space-y-2">

            <Label>
              Age
            </Label>

            <Input
              type="number"
              className={inputClass}
              value={form.age}
              onChange={(e) =>
                setForm({
                  ...form,
                  age: e.target.value,
                })
              }
              placeholder="Enter age"
            />

            {errors.age && (

              <p className="text-red-400 text-sm">
                {errors.age}
              </p>

            )}

          </div>

          {/* GENDER */}
          <div className="space-y-2">

            <Label>
              Gender
            </Label>

            <Select
              value={form.gender}
              onValueChange={(value) =>
                setForm({
                  ...form,
                  gender: value,
                })
              }
            >

              <SelectTrigger className={inputClass}>
                <SelectValue placeholder="Select Gender" />
              </SelectTrigger>

              <SelectContent className={selectContentClass}>

                <SelectItem
                  value="Male"
                  className={selectItemClass}
                >
                  Male
                </SelectItem>

                <SelectItem
                  value="Female"
                  className={selectItemClass}
                >
                  Female
                </SelectItem>

                <SelectItem
                  value="Other"
                  className={selectItemClass}
                >
                  Other
                </SelectItem>

              </SelectContent>

            </Select>

            {errors.gender && (

              <p className="text-red-400 text-sm">
                {errors.gender}
              </p>

            )}

          </div>

          {/* COMPLAINT */}
          <div className="space-y-2">

            <Label>
              Chief Complaint
            </Label>

            <Select
              value={form.complaint}
              onValueChange={(value) =>
                setForm({
                  ...form,
                  complaint: value,
                })
              }
            >

              <SelectTrigger className={inputClass}>
                <SelectValue placeholder="Select Complaint" />
              </SelectTrigger>

              <SelectContent className={selectContentClass}>

                <SelectItem
                  value="Chest Pain"
                  className={selectItemClass}
                >
                  Chest Pain
                </SelectItem>

                <SelectItem
                  value="Breathing Difficulty"
                  className={selectItemClass}
                >
                  Breathing Difficulty
                </SelectItem>

                <SelectItem
                  value="Fever"
                  className={selectItemClass}
                >
                  Fever
                </SelectItem>

                <SelectItem
                  value="Accident / Trauma"
                  className={selectItemClass}
                >
                  Accident / Trauma
                </SelectItem>

                <SelectItem
                  value="Head Injury"
                  className={selectItemClass}
                >
                  Head Injury
                </SelectItem>

                <SelectItem
                  value="Abdominal Pain"
                  className={selectItemClass}
                >
                  Abdominal Pain
                </SelectItem>

              </SelectContent>

            </Select>

            {errors.complaint && (

              <p className="text-red-400 text-sm">
                {errors.complaint}
              </p>

            )}

          </div>

          {/* PAIN LEVEL */}
          <div className="col-span-1 md:col-span-2 space-y-3">

            <Label>
              Pain Level: {form.painLevel}
            </Label>

            <input
              type="range"
              min="1"
              max="10"
              value={form.painLevel}
              onChange={(e) =>
                setForm({
                  ...form,
                  painLevel: Number(
                    e.target.value
                  ),
                })
              }
              className="w-full accent-blue-500"
            />

          </div>

          {/* BREATHING */}
          <div className="space-y-2">

            <Label>
              Breathing Status
            </Label>

            <Select
              value={form.breathing}
              onValueChange={(value) =>
                setForm({
                  ...form,
                  breathing: value,
                })
              }
            >

              <SelectTrigger className={inputClass}>
                <SelectValue placeholder="Select Status" />
              </SelectTrigger>

              <SelectContent className={selectContentClass}>

                <SelectItem
                  value="Normal"
                  className={selectItemClass}
                >
                  Normal
                </SelectItem>

                <SelectItem
                  value="Difficulty"
                  className={selectItemClass}
                >
                  Difficulty
                </SelectItem>

                <SelectItem
                  value="Severe Distress"
                  className={selectItemClass}
                >
                  Severe Distress
                </SelectItem>

              </SelectContent>

            </Select>

            {errors.breathing && (

              <p className="text-red-400 text-sm">
                {errors.breathing}
              </p>

            )}

          </div>

          {/* CONSCIOUSNESS */}
          <div className="space-y-2">

            <Label>
              Consciousness
            </Label>

            <Select
              value={form.consciousness}
              onValueChange={(value) =>
                setForm({
                  ...form,
                  consciousness: value,
                })
              }
            >

              <SelectTrigger className={inputClass}>
                <SelectValue placeholder="Select Consciousness" />
              </SelectTrigger>

              <SelectContent className={selectContentClass}>

                <SelectItem
                  value="Alert"
                  className={selectItemClass}
                >
                  Alert
                </SelectItem>

                <SelectItem
                  value="Drowsy"
                  className={selectItemClass}
                >
                  Drowsy
                </SelectItem>

                <SelectItem
                  value="Unconscious"
                  className={selectItemClass}
                >
                  Unconscious
                </SelectItem>

              </SelectContent>

            </Select>

            {errors.consciousness && (

              <p className="text-red-400 text-sm">
                {errors.consciousness}
              </p>

            )}

          </div>

          {/* DEPARTMENT */}
          <div className="space-y-2">

            <Label>
              Department
            </Label>

            <Select
              value={
                form.departmentId
                  ? String(form.departmentId)
                  : ""
              }
              onValueChange={(value) =>
                setForm({
                  ...form,
                  departmentId:
                    Number(value),
                })
              }
            >

              <SelectTrigger className={inputClass}>
                <SelectValue placeholder="Select Department" />
              </SelectTrigger>

              <SelectContent className={selectContentClass}>

                {departments?.map((dept) => (

                  <SelectItem
                    key={dept.id}
                    value={String(dept.id)}
                    className={selectItemClass}
                  >
                    {dept.name}
                  </SelectItem>

                ))}

              </SelectContent>

            </Select>

            {errors.departmentId && (

              <p className="text-red-400 text-sm">
                {errors.departmentId}
              </p>

            )}

          </div>

          {/* ASSIGNED DOCTOR */}
          <div className="space-y-2">

            <Label>
              Assigned Doctor
            </Label>

            <Input
              className={inputClass}
              value={form.assignedDoctor}
              onChange={(e) =>
                setForm({
                  ...form,
                  assignedDoctor:
                    e.target.value,
                })
              }
              placeholder="Enter doctor name"
            />

          </div>

          {/* MEDICAL CONDITION */}
          <div className="space-y-2">

            <Label>
              Medical Condition
            </Label>

            <Input
              className={inputClass}
              value={form.medicalCondition}
              onChange={(e) =>
                setForm({
                  ...form,
                  medicalCondition:
                    e.target.value,
                })
              }
              placeholder="Describe condition"
            />

          </div>

          {/* CRITICAL */}
          <div className="flex items-center gap-3 pt-4">

            <Checkbox
              checked={form.critical}
              onCheckedChange={(
                checked
              ) =>
                setForm({
                  ...form,
                  critical:
                    checked === true,
                })
              }
            />

            <Label>
              Critical Patient
            </Label>

          </div>

          {/* EMERGENCY */}
          <div className="flex items-center gap-3 pt-4">

            <Checkbox
              checked={
                form.emergencyOverride
              }
              onCheckedChange={(
                checked
              ) =>
                setForm({
                  ...form,
                  emergencyOverride:
                    checked === true,
                })
              }
            />

            <Label>
              Emergency Override
            </Label>

          </div>

        </div>

        {/* ACTIONS */}
        <div className="flex justify-end gap-4 pt-8">

          <button
            onClick={onClose}
            className="px-6 py-3 rounded-xl border border-white/10 text-slate-300 hover:bg-white/5 transition"
          >
            Cancel
          </button>

          <button
            onClick={handleSubmit}
            className="px-6 py-3 rounded-xl bg-blue-500 hover:bg-blue-600 transition text-white font-medium"
          >
            Add Patient
          </button>

        </div>

      </div>

    </div>
  );
}