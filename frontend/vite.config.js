import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// Replace this with your Render backend URL
const BACKEND_URL = process.env.BACKEND_URL || "http://localhost:8080";

export default defineConfig({
  plugins: [react()],
  server: {
    host: "127.0.0.1",
    port: 5176,
    proxy: {
      // Only proxy API routes, leave static files untouched
      "/auth": {
        target: BACKEND_URL,
        changeOrigin: true,
        secure: BACKEND_URL.startsWith("https"),
        rewrite: (path) => path,
      },
      "/theatres": {
        target: BACKEND_URL,
        changeOrigin: true,
        secure: BACKEND_URL.startsWith("https"),
        rewrite: (path) => path,
      },
      "/movies": {
        target: BACKEND_URL,
        changeOrigin: true,
        secure: BACKEND_URL.startsWith("https"),
        rewrite: (path) => path,
      },
      "/seats": {
        target: BACKEND_URL,
        changeOrigin: true,
        secure: BACKEND_URL.startsWith("https"),
        rewrite: (path) => path,
      },
      "/tickets": {
        target: BACKEND_URL,
        changeOrigin: true,
        secure: BACKEND_URL.startsWith("https"),
        rewrite: (path) => path,
      },
      "/movie-schedules": {
        target: BACKEND_URL,
        changeOrigin: true,
        secure: BACKEND_URL.startsWith("https"),
        rewrite: (path) => path,
      },
    },
  },
});
