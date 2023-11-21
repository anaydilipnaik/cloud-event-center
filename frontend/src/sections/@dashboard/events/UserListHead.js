import PropTypes from "prop-types";
import { TableRow, TableCell, TableHead } from "@mui/material";

UserListHead.propTypes = {
  headLabel: PropTypes.array,
};

export default function UserListHead({ headLabel }) {
  return (
    <TableHead>
      <TableRow>
        <TableCell padding="checkbox" />
        {headLabel.map((headCell) => (
          <TableCell
            key={headCell.id}
            align={headCell.alignRight ? "right" : "left"}
          >
            {headCell.label}
          </TableCell>
        ))}
      </TableRow>
    </TableHead>
  );
}
