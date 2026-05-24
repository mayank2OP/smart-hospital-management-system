import { useMutation, useQueryClient } from "@tanstack/react-query";
import toast from "react-hot-toast";

import { addPatient } from "../services/patientApi";

interface AddPatientPayload {
  name: string;
  age: string | number;
  gender: string;
  complaint: string;
  painLevel: number;
  breathing: string;
  consciousness: string;
  departmentId: number;
  emergencyOverride: boolean;
}

export const useAddPatient = () => {

  const queryClient = useQueryClient();

  return useMutation({

    mutationFn: async (
      payload: AddPatientPayload
    ) => {

      const formattedPayload = {
        ...payload,

        age: Number(payload.age),

        painLevel: Number(
          payload.painLevel
        ),

        departmentId: Number(
          payload.departmentId
        ),
      };

      return addPatient(
        formattedPayload
      );

    },

    onSuccess: () => {

      toast.success(
        "Patient added successfully"
      );

      queryClient.invalidateQueries({
        queryKey: ["queue"],
      });

      queryClient.invalidateQueries({
        queryKey: ["logs"],
      });

      queryClient.invalidateQueries({
        queryKey: ["next"],
      });

    },

    onError: (error: any) => {

      console.error(
        "Add patient error:",
        error
      );

      toast.error(
        error?.response?.data?.message ||
        "Failed to add patient"
      );

    },

  });

};