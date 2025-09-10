import { useState } from "react";
import { Alert, Stack, IconButton } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';

const CreateMovieShow = ({ showTimes, setShowTimes, maxShows }) => {
  const [showTime, setShowTime] = useState("");
  const [showLimitAlert, setShowLimitAlert] = useState(false);

  const handleAddShowTime = (e) => {
    e.preventDefault();

    if (showTimes.length >= maxShows) {
      setShowLimitAlert(true);

      // Hide alert after 3 seconds
      setTimeout(() => setShowLimitAlert(false), 5000);
      return;
    }

    if (!showTime) return;

    setShowTimes([...showTimes, showTime]);
    setShowTime("");
  };

  const handleCloseAlert = () => {
    setShowLimitAlert(false);
  };

  return (
    <Stack spacing={1} sx={{ mb: 2 }}>
      <div>
        <label className="form-label fw-bold p-2">Select show time:</label>
        <input
          type="time"
          className="border border-dark p-2 rounded m-2"
          value={showTime}
          onChange={(e) => setShowTime(e.target.value)}
        />
        <button className="btn btn-primary" onClick={handleAddShowTime}>
          Add
        </button>
      </div>

      {showLimitAlert && (
        <Alert
          severity="warning"
          variant="outlined"
          action={
            <IconButton
              aria-label="close"
              color="inherit"
              size="small"
              onClick={handleCloseAlert}
            >
              <CloseIcon fontSize="inherit" />
            </IconButton>
          }
        >
          You can only add {maxShows} show times.
        </Alert>
      )}
    </Stack>
  );
};

export default CreateMovieShow;
