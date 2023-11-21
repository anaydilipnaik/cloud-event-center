import PropTypes from "prop-types";
import { Link as RouterLink } from "react-router-dom";
import { useTheme } from "@mui/material/styles";
import cecLogo from "../assets/1142px-Cec-logo.png";

Logo.propTypes = {
  disabledLink: PropTypes.bool,
  sx: PropTypes.object,
};

export default function Logo({ disabledLink = false, sx }) {
  const theme = useTheme();

  const logo = (
    <img
      style={{
        width: 40,
        height: 40,
      }}
      src={cecLogo}
    />
  );

  if (disabledLink) {
    return <>{logo}</>;
  }

  return <RouterLink to="/update-user">{logo}</RouterLink>;
}
