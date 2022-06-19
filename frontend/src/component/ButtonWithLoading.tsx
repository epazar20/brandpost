import React from "react";

const ButtonWithLoading = (props: any) => {
  const { onClick, disabled, pendingApiCall, text, error, className } = props;
  return (
    <div className={className ? "" : "form-group text-center mt-4"}>
      <button
        className={className || "btn btn-primary"}
        onClick={onClick}
        disabled={disabled}
      >
        {text}
        {pendingApiCall && (
          <span className="spinner-border spinner-border-sm"></span>
        )}
      </button>
      {error && (
        <div className="alert alert-danger" role="alert">
          {error}
        </div>
      )}
    </div>
  );
};

export default ButtonWithLoading;
