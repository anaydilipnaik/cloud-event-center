import PropTypes from "prop-types";
import { Typography } from "@mui/material";

SearchNotFound.propTypes = {
  searchQuery: PropTypes.string,
};

export default function SearchNotFound({}) {
  return (
    <Typography gutterBottom align="center" variant="subtitle1">
      No Results found
    </Typography>
  );
}
