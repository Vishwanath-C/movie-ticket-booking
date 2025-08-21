const AddShowTime = () => {
    return (
        <>
            <label className="form-label fw-bold p-2">Select show time : </label>
            <input type="time" className="border border-dark p-2 rounded m-3"/>
            <button className="btn btn-primary"> Add another show</button>
        </>
    );
};

export default AddShowTime;