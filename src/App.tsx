import Dashboard from "./pages/Dashboard";

import {
  Toaster,
} from "react-hot-toast";

export default function App() {

  return (

    <div className="min-h-screen bg-gradient-to-br from-[#020617] via-[#020617] to-[#0f172a] text-white overflow-hidden">

      {/* GLOBAL TOASTER */}
      <Toaster

        position="top-right"

        toastOptions={{

          duration: 3000,

          style: {
            background: "#0f172a",
            color: "#ffffff",
            border:
              "1px solid rgba(255,255,255,0.1)",
            borderRadius: "16px",
            padding: "14px 18px",
          },

          success: {
            iconTheme: {
              primary: "#22c55e",
              secondary: "#fff",
            },
          },

          error: {
            iconTheme: {
              primary: "#ef4444",
              secondary: "#fff",
            },
          },

        }}

      />

      {/* MAIN APP */}
      <Dashboard />

    </div>

  );

}