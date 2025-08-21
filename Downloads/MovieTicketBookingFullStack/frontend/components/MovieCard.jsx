
import { useNavigate } from "react-router-dom";

const MovieCard = ({ movie }) => {
  const role = localStorage.getItem('role');
  const navigate = useNavigate();

  // const handleBooking = () => {
  //   navigate('/app/bookings', { state: { movie } });
  // };

  const handleBooking = () => {
    navigate(`/app/bookings/${movie.id}`);
  };

  return (
    <div className="card h-100 shadow-sm border border-dark">
      <div className="card-body">
        <h4 className="fw-bold text-center bg-primary text-white p-2 border rounded">{movie.title}</h4>
        <p className="fst-italic">{movie.description}</p>
        <div className="d-flex mb-1">
          <p className="fw-semibold">Duration</p> : {movie.duration} minutes
        </div>
        <div className="mt-auo text-center">
          {role === 'USER' && (
            <button className="btn btn-primary" onClick={handleBooking}>Book</button>
          )}
        </div>

      </div>
    </div>
  );
};

export default MovieCard;
