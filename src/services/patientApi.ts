import { api } from "./api";

/* =========================
   TYPES
========================= */

export interface Patient {
  id: number;
  name: string;
  age: number;
  gender: string;
  complaint: string;
  severity: number;
  breathing: string;
  consciousness: string;
  waitingTime: number;
  status: string;
  departmentId: number;

  // =========================
  // NEW FIELDS
  // =========================
  assignedDoctor?: string;
  medicalCondition?: string;
  critical?: boolean;
}

export interface AddPatientPayload {
  name: string;
  age: number;
  gender: string;
  complaint: string;
  painLevel: number;
  breathing: string;
  consciousness: string;
  departmentId: number;

  // =========================
  // NEW FIELDS
  // =========================
  assignedDoctor?: string;
  medicalCondition?: string;
  critical?: boolean;

  emergencyOverride: boolean;
}

export interface ApiResponse<T> {
  message?: string;
  data: T;
}

/* =========================
   GET QUEUE
========================= */

export const getQueue = async (
  deptId: number
): Promise<ApiResponse<Patient[]>> => {

  const response = await api.get(
    `/patients/queue/${deptId}`
  );

  return response.data;

};

/* =========================
   NEXT PATIENT
========================= */

export const getNextPatient =
  async (
    deptId: number
  ): Promise<ApiResponse<Patient>> => {

    const response = await api.get(
      `/patients/next/${deptId}`
    );

    return response.data;

  };

/* =========================
   TREAT PATIENT
========================= */

export const treatPatient =
  async (
    id: number
  ): Promise<ApiResponse<Patient>> => {

    const response = await api.put(
      `/patients/${id}/treat`
    );

    return response.data;

  };

/* =========================
   ADD PATIENT
========================= */

export const addPatient =
  async (
    data: AddPatientPayload
  ): Promise<ApiResponse<Patient>> => {

    const payload = {

      name: data.name,

      age: Number(data.age),

      gender: data.gender,

      complaint: data.complaint,

      painLevel: Number(
        data.painLevel
      ),

      breathing:
        data.breathing,

      consciousness:
        data.consciousness,

      departmentId: Number(
        data.departmentId
      ),

      // =========================
      // NEW FIELDS
      // =========================
      assignedDoctor:
        data.assignedDoctor,

      medicalCondition:
        data.medicalCondition,

      critical:
        data.critical,

      emergencyOverride:
        data.emergencyOverride,
    };

    const response =
      await api.post(
        "/patients",
        payload
      );

    return response.data;

  };