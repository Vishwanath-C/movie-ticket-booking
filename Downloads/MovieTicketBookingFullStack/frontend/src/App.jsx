
import { useState } from 'react';
import { Route, Routes } from 'react-router-dom';
import Bookings from '../components/Bookings';
import DashboardLayout from '../components/DashboardLayout';
import Login from '../components/Login';
import MovieActions from '../components/MovieActions';
import MyBookings from '../components/MyBookings';
import ProtectedRoute from '../components/ProtectedRoute';
import Register from '../components/Register';
import Theatres from '../components/Theatres';


import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

import { useNavigate } from 'react-router-dom';
import HomePage from '../components/HomePage';
import ShowSeatLayoutDup from '../components/ShowSeatLayoutDup';


function App() {
  const [open, setOpen] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();
  const [movie, setMovie] = useState(null);


  return (
    <>
      <div className='bg-primary text-white w-100 p-1 fixed-top shadow-sm'>
        <h2 className='text-center m-0 p-4'>Movie Ticket Booking System</h2>
      </div>

      <div style={{ paddingTop: '50px' }}>
        
        <Routes>
          <Route path="/login" element={<Login isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />} />
          <Route path="/register" element={<Register />} />
          <Route path="/" element={<HomePage isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />} />

          <Route
            path="/app"
            element={
              <ProtectedRoute setIsLoggedIn={setIsLoggedIn}>
                <DashboardLayout/>
              </ProtectedRoute>
            }
          >
            <Route path="movies" element={<MovieActions />} />
            <Route path='theatres' element={<Theatres />} />            
            <Route path='tickets' element={<MyBookings />} />
            <Route path='seatlayout' element={<ShowSeatLayoutDup />} />
            <Route path="bookings/:movieId" element={<Bookings />} />
          </Route>
        </Routes>
      </div >
    </>
  )
}

export default App
