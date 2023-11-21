import PropTypes from "prop-types";
import { styled } from "@mui/material/styles";
import {
  Toolbar,
  Typography,
  OutlinedInput,
  InputAdornment,
} from "@mui/material";
import Iconify from "../../../mui_components/Iconify";

const RootStyle = styled(Toolbar)(({ theme }) => ({
  height: 96,
  display: "flex",
  justifyContent: "space-between",
  padding: theme.spacing(0, 1, 0, 3),
}));

const SearchStyle = styled(OutlinedInput)(({ theme }) => ({
  width: 240,
  transition: theme.transitions.create(["box-shadow", "width"], {
    easing: theme.transitions.easing.easeInOut,
    duration: theme.transitions.duration.shorter,
  }),
  "& fieldset": {
    borderWidth: `1px !important`,
    borderColor: `${theme.palette.grey[500_32]} !important`,
  },
}));

UserListToolbar.propTypes = {
  numSelected: PropTypes.number,
  filterName: PropTypes.string,
  onFilterName: PropTypes.func,
};

export default function UserListToolbar({ isRegistration = false }) {
  return !isRegistration ? (
    <RootStyle
      sx={{
        color: "primary.main",
        bgcolor: "primary.lighter",
      }}
    >
      <SearchStyle
        placeholder="Search Event..."
        startAdornment={
          <InputAdornment position="start">
            <Iconify
              icon="eva:search-fill"
              sx={{ color: "text.disabled", width: 20, height: 20 }}
            />
          </InputAdornment>
        }
      />
    </RootStyle>
  ) : null;
}
