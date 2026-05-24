import { useQuery } from "@tanstack/react-query";

import { api } from "../services/api";

/* =========================
   TYPES
========================= */

export interface ActivityLog {
  id: number;
  action: string;
  timestamp?: string;
}

/* =========================
   FETCH LOGS
========================= */

const fetchLogs = async (): Promise<
  ActivityLog[]
> => {

  const response = await api.get(
    "/logs/recent"
  );

  return response.data;

};

/* =========================
   HOOK
========================= */

export const useLogs = () => {

  return useQuery<ActivityLog[]>({

    queryKey: ["logs"],

    queryFn: fetchLogs,

    refetchInterval: 5000,

    staleTime: 3000,

    retry: 2,

    refetchOnWindowFocus: true,

  });

};