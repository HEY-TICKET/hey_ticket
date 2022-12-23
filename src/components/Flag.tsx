import React from "react";

export interface FlagProps {
  children: string;
  className?: string;
}

const Flag = ({ children, className = "" }: FlagProps) => {
  return (
    <div
      className={`w-fit px-1 m-0 text-xs bg-jin-blue-400 text-white tracking-tight my-0.5 ${className}`}
    >
      {children}
    </div>
  );
};

export default Flag;
