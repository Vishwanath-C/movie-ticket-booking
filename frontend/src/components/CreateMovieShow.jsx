import { Button, Stack, TextField, Typography } from "@mui/material";
import { useState } from "react";

const CreateMovieShow = ({ showTimes, setShowTimes, maxShows }) => {
  const [showTime, setShowTime] = useState("");
  const [showLimitAlert, setShowLimitAlert] = useState(false);

  const handleAddShowTime = () => {
    if (!showTime) return;

    if (showTimes.length >= maxShows) {
      setShowLimitAlert(true);
      setTimeout(() => setShowLimitAlert(false), 3000);
      return;
    }

    setShowTimes([...showTimes, showTime]);
    setShowTime("");
  };

  const handleCloseAlert = () => setShowLimitAlert(false);

  return (
    <Stack spacing={1} sx={{ mb: 2 }}>
      <Stack direction="row" spacing={1} alignItems="center">
        <TextField
          type="time"
          label="Select show time"
          value={showTime}
          onChange={(e) => setShowTime(e.target.value)}
          size="small"
          sx={{ width: 150 }}
          // error={maxReached}
          // helperText={maxReached ? `Maximum ${maxShows} show times reached.` : " "}
          InputLabelProps={{ shrink: true }}
        />

        <Button
          variant="contained"
          color="primary"
          onClick={handleAddShowTime}
          disabled={showTimes.length >= maxShows}
        >
          Add
        </Button>
      </Stack>

      {showTimes.length >= maxShows && (
        <Typography variant="caption" color="warning.main">
          Maximum {maxShows} show times reached.
        </Typography>
      )}


    </Stack>
  );
};

export default CreateMovieShow;
