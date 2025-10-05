import { Button, CssBaseline, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from "@mui/material";
import { useEffect, useState } from "react";
import { Route, Routes, useNavigate } from "react-router-dom";

import HomePage from "./components/HomePage";
import Login from "./components/Login";
import ProtectedRoute from "./components/ProtectedRoute";
import PublicLayout from "./components/PublicLayout";
import Register from "./components/Register";

import AddMovieForm from "./components/AddMovieForm";
import AddTheatreAndSeats from "./components/AddTheatreAndSeats";
import AssignMovieToTheatre from "./components/AssignMovieToTheatre";
import Bookings from "./components/Bookings";
import FinishedBookings from "./components/FinishedBookings";
import MovieActions from "./components/MovieActions";
import ShowSeatLayoutDup from "./components/ShowSeatLayoutDup";
import Theatres from "./components/Theatres";
import TicketPage from "./components/TicketPage";
import UpcomingBookings from "./components/UpcomingBookings";
import DashboardLayout from "./components/DashboardLayout";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [logoutOpen, setLogoutOpen] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) setIsLoggedIn(true);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    setIsLoggedIn(false);
    setLogoutOpen(false);
    navigate("/login");
  };

  return (
    <>
      <CssBaseline />

      <Routes>
        {/* Public routes */}
        <Route element={<PublicLayout />}>
          <Route path="/" element={<HomePage isLoggedIn={isLoggedIn} />} />
          <Route path="/login" element={<Login setIsLoggedIn={setIsLoggedIn} />} />
          <Route path="/register" element={<Register />} />
        </Route>

        {/* Protected routes */}
        <Route
          path="/app/*"
          element={<ProtectedRoute setIsLoggedIn={setIsLoggedIn} />}
        >
          <Route element={<DashboardLayout setIsLoggedIn={setIsLoggedIn} isLoggedIn={isLoggedIn} />}>
            <Route path="home" element={<MovieActions />} />
            <Route path="movies" element={<MovieActions nowrunning={true} />} />
            <Route path="movies/upcoming" element={<MovieActions nowrunning={false} />} />
            <Route path="movies/new" element={<AddMovieForm />} />
            <Route path="theatres" element={<Theatres />} />
            <Route path="theatres/new" element={<AddTheatreAndSeats />} />
            <Route path="theatres/assign-movie" element={<AssignMovieToTheatre />} />
            <Route path="tickets/upcoming" element={<UpcomingBookings />} />
            <Route path="tickets/history" element={<FinishedBookings />} />
            <Route path="seatlayout" element={<ShowSeatLayoutDup />} />
            <Route path="bookings/:movieId" element={<Bookings />} />
            <Route path="tickets/view" element={<TicketPage />} />
          </Route>
        </Route>
      </Routes>



      {/* Logout Dialog */}
      <Dialog open={logoutOpen} onClose={() => setLogoutOpen(false)} sx={{ "& .MuiPaper-root": { borderRadius: 3, padding: 2, minWidth: 300 } }}>
        <DialogTitle sx={{ textAlign: "center", fontWeight: "bold", fontSize: "1.5rem" }}>Log out</DialogTitle>
        <DialogContent>
          <DialogContentText sx={{ textAlign: "center", fontSize: "1rem" }}>
            Are you sure you want to logout?
          </DialogContentText>
        </DialogContent>
        <DialogActions sx={{ justifyContent: "center", gap: 2, paddingBottom: 2 }}>
          <Button variant="outlined" onClick={() => setLogoutOpen(false)}>Cancel</Button>
          <Button variant="contained" color="error" onClick={handleLogout}>Logout</Button>
        </DialogActions>
      </Dialog>
    </>
  );
}

export default App;
