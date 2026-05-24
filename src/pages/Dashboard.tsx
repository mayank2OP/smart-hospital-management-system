import { useEffect, useMemo, useState } from "react";

import { motion } from "framer-motion";

import toast from "react-hot-toast";

import AddPatientModal from "../components/AddPatientModal";

import { useAddPatient } from "../hooks/useAddPatient";
import { useLogs } from "../hooks/useLogs";
import { useNextPatient } from "../hooks/useNextPatient";
import { useQueue } from "../hooks/useQueue";
import { useTreat } from "../hooks/useTreat";

interface Department {
  id: number;
  name: string;
}

export default function Dashboard() {

  const [selectedDept, setSelectedDept] =
    useState<number>(1);

  const [openModal, setOpenModal] =
    useState(false);

  const [localQueue, setLocalQueue] =
    useState<any[]>([]);

  // =========================
  // API HOOKS
  // =========================

  const {
    data,
    isLoading,
  } = useQueue(selectedDept);

  const {
    data: nextPatient,
  } = useNextPatient(selectedDept);

  const {
    data: logs,
  } = useLogs();

  const addPatientMutation =
    useAddPatient();

  const treatMutation =
    useTreat();

  // =========================
  // LOCAL STATE SYNC
  // =========================

  useEffect(() => {

    if (
      Array.isArray(data?.data)
    ) {

      setLocalQueue(data.data);

    }

  }, [data]);

  // =========================
  // DEPARTMENTS
  // =========================

  const departments: Department[] = [

    { id: 1, name: "Emergency" },
    { id: 2, name: "Cardiology" },
    { id: 3, name: "Neurology" },
    { id: 4, name: "Orthopedics" },
    { id: 5, name: "Pediatrics" },
    { id: 6, name: "Dermatology" },
    { id: 7, name: "Radiology" },
    { id: 8, name: "ICU" },
    { id: 9, name: "General Surgery" },
    { id: 10, name: "ENT" },
    { id: 11, name: "Oncology" },
    { id: 12, name: "Psychiatry" },

  ];

  // =========================
  // ANALYTICS
  // =========================

  const criticalCount =
    useMemo(() => {

      return localQueue.filter(
        (p) => p.severity >= 8
      ).length;

    }, [localQueue]);

  const avgWait =
    useMemo(() => {

      if (
        localQueue.length === 0
      ) return 0;

      return Math.round(

        localQueue.reduce(
          (acc, p) =>
            acc + (
              p.waitingTime || 0
            ),
          0
        ) / localQueue.length

      );

    }, [localQueue]);

  // =========================
  // TREAT PATIENT
  // =========================

  const handleTreat =
    async (
      patientId: number
    ) => {

      // OPTIMISTIC UPDATE
      setLocalQueue((prev) =>

        prev.filter(
          (p) =>
            p.id !== patientId
        )

      );

      try {

        await treatMutation.mutateAsync(
          patientId
        );

        toast.success(
          "Patient treated"
        );

      } catch (error) {

        toast.error(
          "Failed to treat patient"
        );

      }

    };

  return (

    <div className="min-h-screen bg-[#020617] text-white flex">

      {/* SIDEBAR */}

      <aside className="w-64 border-r border-white/10 p-5 hidden md:block">

        <h1 className="text-4xl font-bold mb-8">
          Departments
        </h1>

        <div className="space-y-3">

          {departments.map((dept) => (

            <button
              key={dept.id}
              onClick={() =>
                setSelectedDept(
                  dept.id
                )
              }
              className={`
                w-full
                text-left
                px-4
                py-4
                rounded-2xl
                border
                transition-all
                duration-200
                ${
                  selectedDept === dept.id
                    ? "bg-blue-500/10 border-blue-400/20 text-blue-300"
                    : "bg-white/[0.03] border-white/[0.06] hover:bg-white/[0.05]"
                }
              `}
            >
              {dept.name}
            </button>

          ))}

        </div>

      </aside>

      {/* MAIN */}

      <main className="flex-1 p-6 overflow-auto">

        {/* HEADER */}

        <div className="flex flex-wrap items-center justify-between gap-4">

          <div>

            <div className="flex items-center gap-4">

              <h1 className="text-6xl font-bold">
                ER Queue
              </h1>

              <div className="px-3 py-1 rounded-full bg-white/5 border border-white/10 text-sm">

                {localQueue.length} active

              </div>

              <button
                onClick={() =>
                  setOpenModal(true)
                }
                className="
                  bg-blue-600
                  hover:bg-blue-500
                  px-6
                  py-3
                  rounded-2xl
                  font-semibold
                  transition-all
                "
              >
                + Add Patient
              </button>

            </div>

            <p className="text-slate-400 mt-3">
              Live Emergency Triage Monitoring
            </p>

          </div>

          <div className="bg-white/[0.03] border border-white/[0.06] rounded-2xl px-6 py-4">

            <div className="flex items-center gap-3">

              <div className="w-3 h-3 rounded-full bg-green-400 animate-pulse" />

              <div>

                <p className="font-semibold">
                  Live Monitoring
                </p>

                <p className="text-xs text-slate-400">
                  Last updated:
                  {" "}
                  {new Date().toLocaleTimeString()}
                </p>

              </div>

            </div>

          </div>

        </div>

        {/* ANALYTICS */}

        <div className="grid grid-cols-1 md:grid-cols-3 gap-5 mt-8">

          <div className="bg-white/[0.03] border border-white/[0.06] rounded-3xl p-6">

            <p className="text-slate-400">
              Total Patients
            </p>

            <h2 className="text-5xl font-bold mt-3">

              {localQueue.length}

            </h2>

          </div>

          <div className="bg-white/[0.03] border border-white/[0.06] rounded-3xl p-6">

            <p className="text-slate-400">
              Critical Patients
            </p>

            <h2 className="text-5xl font-bold text-red-400 mt-3">

              {criticalCount}

            </h2>

          </div>

          <div className="bg-white/[0.03] border border-white/[0.06] rounded-3xl p-6">

            <p className="text-slate-400">
              Avg Wait Time
            </p>

            <h2 className="text-5xl font-bold text-blue-400 mt-3">

              {avgWait}m

            </h2>

          </div>

        </div>

        {/* QUEUE */}

        <div className="mt-10">

          <h2 className="text-5xl font-bold mb-8">
            Patient Queue
          </h2>

          {isLoading ? (

            <div className="text-slate-400">
              Loading...
            </div>

          ) : localQueue.length === 0 ? (

            <div className="text-center py-32 text-slate-500">

              <h3 className="text-3xl">
                System Idle
              </h3>

              <p className="mt-4">
                Waiting for incoming patients
              </p>

            </div>

          ) : (

            <div className="space-y-4">

              {localQueue.map(

                (patient, index) => (

                  <motion.div
                    key={patient.id}
                    layout
                    initial={{
                      opacity: 0,
                      y: 10,
                    }}
                    animate={{
                      opacity: 1,
                      y: 0,
                    }}
                    className="
                      bg-white/[0.03]
                      border
                      border-white/[0.06]
                      rounded-3xl
                      p-6
                      flex
                      flex-wrap
                      items-center
                      justify-between
                      gap-5
                    "
                  >

                    <div>

                      <p className="text-slate-400 text-sm">
                        Queue #{index + 1}
                      </p>

                      <h3 className="text-2xl font-bold mt-1">

                        {patient.name}

                      </h3>

                    </div>

                    <div className="flex items-center gap-5">

                      <div className="text-center">

                        <p className="text-slate-400 text-sm">
                          Severity
                        </p>

                        <p className="text-xl font-bold text-red-400">

                          {patient.severity}

                        </p>

                      </div>

                      <div className="text-center">

                        <p className="text-slate-400 text-sm">
                          Wait
                        </p>

                        <p className="text-xl font-bold">

                          {patient.waitingTime || 0}m

                        </p>

                      </div>

                      <button
                        disabled={
                          treatMutation.isPending
                        }
                        onClick={() =>
                          handleTreat(
                            patient.id
                          )
                        }
                        className="
                          bg-blue-600
                          hover:bg-blue-500
                          disabled:opacity-50
                          px-5
                          py-3
                          rounded-2xl
                          font-semibold
                          transition-all
                        "
                      >
                        {
                          treatMutation.isPending
                            ? "Treating..."
                            : "Treat"
                        }
                      </button>

                    </div>

                  </motion.div>

                )

              )}

            </div>

          )}

        </div>

        {/* RIGHT PANEL */}

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-5 mt-10">

          {/* NEXT PATIENT */}

          <div className="bg-white/[0.03] border border-white/[0.06] rounded-3xl p-6">

            <p className="text-slate-400 text-sm">
              NEXT PATIENT
            </p>

            <h2 className="text-5xl font-bold mt-4">

              {
                nextPatient?.data?.name ||
                "No Patient"
              }

            </h2>

          </div>

          {/* LOGS */}

          <div className="bg-white/[0.03] border border-white/[0.06] rounded-3xl p-6">

            <h2 className="text-3xl font-bold mb-5">

              Recent Activity

            </h2>

            <div className="space-y-3">

              {Array.isArray((logs as any)?.data) ? (

                (logs as any).data
                  .slice(0, 5)
                  .map((log: any) => (

                    <div
                      key={log.id}
                      className="
                        bg-white/[0.03]
                        rounded-2xl
                        px-4
                        py-3
                      "
                    >

                      {log.action}

                    </div>

                  ))

              ) : (

                <p className="text-slate-500">
                  No recent activity
                </p>

              )}

            </div>

          </div>

        </div>

        {/* MODAL */}

        {openModal && (

          <AddPatientModal

            onClose={() =>
              setOpenModal(false)
            }

            departments={departments}

            onSubmit={async (data) => {

              try {

                await addPatientMutation.mutateAsync(
                  data
                );

                toast.success(
                  "Patient added successfully"
                );

                setOpenModal(false);

              } catch (error) {

                console.error(error);

                toast.error(
                  "Failed to add patient"
                );

              }

            }}

          />

        )}

      </main>

    </div>

  );
}