import React from 'react';


const Input = props => {

    const { name, error, onChange, defaultValue, type, label } = props;
    let className = "form-control";
    if(error)
    {
        className += " is-invalid"
    }

    return (
        <div className="form-group">
            <label className='mb-2'>{label}</label>
            <input className={className} name={name} onChange={onChange} type={type} defaultValue={defaultValue} />
            <div className="invalid-feedback">{error}</div>
        </div>
    );
}

export default Input;