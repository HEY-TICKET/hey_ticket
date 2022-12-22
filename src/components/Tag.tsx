import React from "react";

export interface TagProps {
  children: string;
  className?: string;
}

const Tag = ({ children, className = "" }: TagProps) => {
  return (
    <div
      className={`w-fit px-1 m-0 text-xs bg-orange-400 text-white tracking-tight my-0.5 ${className}`}
    >
      {children}
    </div>
  );
};

export default Tag;
