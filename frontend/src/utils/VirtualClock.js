import React, { useState, useEffect } from "react";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DesktopDatePicker } from "@mui/x-date-pickers/DesktopDatePicker";
import { TextField, Stack } from "@mui/material";
import { getMimicTime, setMimicTime } from "../controllers/mimicClock";

const VirtualClock = ({ ...props }) => {
  const [value, setValue] = useState(null);

  const handleChange = (newValue) => {
    let mimicDate = newValue.toISOString().split("T")[0];
    setMimicTime(mimicDate)
      .then((res) => {
        if (res.data === "Success") getMimicTimeFunc();
      })
      .catch((err) => console.log(err));
  };

  const getMimicTimeFunc = () => {
    getMimicTime()
      .then((res) => {
        setValue(res.data.mimicDateTime.split("T")[0]);
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    getMimicTimeFunc();
  }, []);

  return (
    <LocalizationProvider dateAdapter={AdapterDateFns}>
      <Stack spacing={3} sx={{ marginRight: "25px" }}>
        <DesktopDatePicker
          inputFormat="yyyy-MM-dd"
          value={value}
          onChange={handleChange}
          renderInput={(params) => <TextField {...params} />}
        />
      </Stack>
    </LocalizationProvider>
  );
};

export default VirtualClock;
