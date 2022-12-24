import React from "react";

interface InputProps {
  placeholder?: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const Input = ({ placeholder = "", value, onChange }: InputProps) => {
  return (
    <input
      className={
        "p-3 border w-full bg-gray-200 rounded placeholder:text-gray-500 focus:bg-white"
      }
      type="text"
      placeholder={placeholder}
      value={value}
      onChange={onChange}
    />
  );
};

export default Input;
