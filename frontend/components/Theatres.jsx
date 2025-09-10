import AddTheatre from "./AddTheatre"
import AddTheatreAndSeats from "./AddTheatreAndSeats";
import AssignMovieToTheatre from "./AssignMovieToTheatre";
import GenerateSeatLayout from "./GenerateSeatLayout";
import ViewTheatres from "./ViewTheatres";

const Theatres = () => {
    const role = localStorage.getItem('role');

    return (
        <>
            {role == 'ADMIN' && <ViewTheatres />}
            {/* {role == 'ADMIN' && <AddTheatreAndSeats/>}
            {/* {role === 'ADMIN' && <AddTheatre />}
            {role === 'ADMIN' && <GenerateSeatLayout/>} */}
            {/* {role === 'ADMIN' && <AssignMovieToTheatre/>} */}
        </>
    );
};

export default Theatres;