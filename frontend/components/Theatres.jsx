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