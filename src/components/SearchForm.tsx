import React, { useState } from "react";

interface SearchFormProps {
  placeholder?: string;
  onSubmit: (value: string) => void;
  className?: string;
}

const SearchForm = ({
  placeholder = "",
  className,
  onSubmit,
}: SearchFormProps) => {
  const [value, setValue] = useState<string>("");

  return (
    <form
      className={`flex items-center rounded-2xl border w-1/2 min-w-[300px] mx-2 px-6 py-1.5 ${className}`}
      onSubmit={(e) => {
        e.preventDefault();
        onSubmit(value);
      }}
    >
      <input
        type="text"
        className={"flex-1 outline-0 mr-2 text-sm"}
        placeholder={placeholder}
        onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
          setValue(e.target.value)
        }
      />
      <button className={"text-orange-400"}>
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
      </button>
    </form>
  );
};

export default SearchForm;
