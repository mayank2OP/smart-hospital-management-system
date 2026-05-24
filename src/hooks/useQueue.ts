import { useQuery } from "@tanstack/react-query";

import {
  getQueue,
  Patient,
  ApiResponse,
} from "../services/patientApi";

export const useQueue = (
  deptId: number
) => {

  return useQuery<
    ApiResponse<Patient[]>
  >({

    queryKey: [
      "queue",
      deptId,
    ],

    queryFn: () =>
      getQueue(deptId),

    enabled: !!deptId,

    // =========================
    // PERFORMANCE OPTIMIZATION
    // =========================

    // LESS AGGRESSIVE REFRESH
    refetchInterval: 15000,

    // CACHE LONGER
    staleTime: 10000,

    // KEEP DATA IN CACHE
    gcTime: 1000 * 60 * 5,

    retry: 1,

    // IMPORTANT
    refetchOnWindowFocus: false,

    // SMOOTHER UX
    refetchOnReconnect: true,

    // PREVENT UI FLICKER
    placeholderData: (
      previousData
    ) => previousData,

  });

};