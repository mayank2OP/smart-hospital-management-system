import { useQuery } from "@tanstack/react-query";

import {
  getNextPatient,
  Patient,
  ApiResponse,
} from "../services/patientApi";

export const useNextPatient = (
  deptId: number
) => {

  return useQuery<
    ApiResponse<Patient>
  >({

    queryKey: [
      "next",
      deptId,
    ],

    queryFn: () =>
      getNextPatient(deptId),

    enabled: !!deptId,

    refetchInterval: 5000,

    staleTime: 3000,

    retry: 2,

    refetchOnWindowFocus: true,

  });

};