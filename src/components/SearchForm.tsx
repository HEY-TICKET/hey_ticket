import React, { useState } from "react";

interface SearchFormProps {
  placeholder?: string;
  onSubmit: (value: string) => void;
  className?: {
    form?: string;
    input?: string;
  };
  icon?: React.ReactNode;
  iconSize?: "md" | "sm";
}

const MagnifyingGlass = (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    fill="none"
    viewBox="0 0 24 24"
    strokeWidth={1.5}
    stroke="currentColor"
    className="w-6 h-6"
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z"
    />
  </svg>
);

const MiniMagnifyingGlass = (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    viewBox="0 0 20 20"
    fill="currentColor"
    className="w-5 h-5"
  >
    <path
      fillRule="evenodd"
      d="M9 3.5a5.5 5.5 0 100 11 5.5 5.5 0 000-11zM2 9a7 7 0 1112.452 4.391l3.328 3.329a.75.75 0 11-1.06 1.06l-3.329-3.328A7 7 0 012 9z"
      clipRule="evenodd"
    />
  </svg>
);

const SearchForm = ({
  placeholder = "",
  className,
  onSubmit,
  iconSize = "md",
}: SearchFormProps) => {
  const [value, setValue] = useState<string>("");

  return (
    <form
      className={`flex items-center rounded-2xl border w-1/2 min-w-[300px] mx-2 px-6 py-1.5 ${className?.form}`}
      onSubmit={(e) => {
        e.preventDefault();
        onSubmit(value);
      }}
    >
      <input
        type="text"
        className={`flex-1 outline-0 mr-2 text-sm ${className?.input}`}
        placeholder={placeholder}
        onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
          setValue(e.target.value)
        }
      />
      <button className={"text-orange-400"}>
        {iconSize === "md" ? MagnifyingGlass : MiniMagnifyingGlass}
      </button>
    </form>
  );
};

export default SearchForm;
