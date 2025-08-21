import { useState } from "react";

// const [showTimes, setShowTimes] = useState([]);

const CreateMovieShow = ({ showTimes, setShowTimes }) => {
    const [showTime, setShowTime] = useState(null);

    const handleAddShowTime = async (e) => {
        e.preventDefault();

        try {
            setShowTimes([...showTimes, showTime]);
            setShowTime(null);
        } catch (error) {
            console.log("Error while creating showtime : ", error);
        }
    };

    return (
        <>
            <div className="mb-4">
                <label className="form-label fw-bold p-2">Select show time : </label>

                <input type="time" className="border border-dark p-2 rounded m-2"
                    value={showTime}
                    onChange={e => setShowTime(e.target.value)} />
                <button className="btn btn-primary" onClick={handleAddShowTime}> Add </button>
            </div>
        </>
    );
};

export default CreateMovieShow;