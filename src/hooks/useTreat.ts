import {
  useMutation,
  useQueryClient,
} from "@tanstack/react-query";

import toast from "react-hot-toast";

import {
  treatPatient,
  Patient,
  ApiResponse,
} from "../services/patientApi";

interface UseTreatOptions {

  onSuccess?: (
    data: ApiResponse<Patient>,
    patientId: number
  ) => void;

  onError?: (
    error: any
  ) => void;

}

export const useTreat = (
  options?: UseTreatOptions
) => {

  const queryClient =
    useQueryClient();

  return useMutation({

    mutationFn: async (
      patientId: number
    ) => {

      const response =
        await treatPatient(
          patientId
        );

      return {
        response,
        patientId,
      };

    },

    // =========================
    // OPTIMISTIC UPDATE
    // =========================
    onMutate: async (
      patientId: number
    ) => {

      // STOP ACTIVE FETCHES
      await queryClient.cancelQueries({
        queryKey: ["queue"],
      });

      // PREVIOUS CACHE
      const previousQueue =
        queryClient.getQueryData([
          "queue",
        ]);

      // REMOVE PATIENT INSTANTLY
      queryClient.setQueryData(
        ["queue"],
        (old: any) => {

          if (!old?.data) {

            return old;

          }

          return {

            ...old,

            data: old.data.filter(
              (p: any) =>
                p.id !== patientId
            ),

          };

        }
      );

      return {
        previousQueue,
      };

    },

    // =========================
    // SUCCESS
    // =========================
    onSuccess: (
      result
    ) => {

      const {
        response,
        patientId,
      } = result;

      toast.success(
        "Patient treated"
      );

      // BACKGROUND REFRESH
      setTimeout(() => {

        queryClient.invalidateQueries({
          queryKey: ["queue"],
        });

        queryClient.invalidateQueries({
          queryKey: ["next"],
        });

        queryClient.invalidateQueries({
          queryKey: ["logs"],
        });

      }, 300);

      if (options?.onSuccess) {

        options.onSuccess(
          response,
          patientId
        );

      }

    },

    // =========================
    // ERROR
    // =========================
    onError: (
      error: any,
      _patientId,
      context
    ) => {

      // RESTORE CACHE
      if (
        context?.previousQueue
      ) {

        queryClient.setQueryData(
          ["queue"],
          context.previousQueue
        );

      }

      console.error(
        "Treat patient error:",
        error
      );

      toast.error(

        error?.response?.data?.message ||

        "Failed to treat patient"

      );

      if (options?.onError) {

        options.onError(error);

      }

    },

  });

};