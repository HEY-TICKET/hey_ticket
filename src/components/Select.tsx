import React, { useEffect, useRef, useState } from "react";

export interface SelectOption {
  value: string;
  label: string;
}

interface SelectProps {
  options: SelectOption[];
  value: SelectOption | null;
  onChange: (value: SelectOption) => void;
  placeholder?: string;
}

const ChevronDown = (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    viewBox="0 0 20 20"
    fill="currentColor"
    className="w-5 h-5"
  >
    <path
      fillRule="evenodd"
      d="M5.23 7.21a.75.75 0 011.06.02L10 11.168l3.71-3.938a.75.75 0 111.08 1.04l-4.25 4.5a.75.75 0 01-1.08 0l-4.25-4.5a.75.75 0 01.02-1.06z"
      clipRule="evenodd"
    />
  </svg>
);

const ChevronUp = (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    viewBox="0 0 20 20"
    fill="currentColor"
    className="w-5 h-5"
  >
    <path
      fillRule="evenodd"
      d="M14.77 12.79a.75.75 0 01-1.06-.02L10 8.832 6.29 12.77a.75.75 0 11-1.08-1.04l4.25-4.5a.75.75 0 011.08 0l4.25 4.5a.75.75 0 01-.02 1.06z"
      clipRule="evenodd"
    />
  </svg>
);

const Select = ({
  options,
  value = null,
  onChange,
  placeholder = "Select...",
}: SelectProps) => {
  const ref = useRef<HTMLDivElement>(null);
  const [showOption, setShowOption] = useState(false);

  const handleItemChange = (value: SelectOption) => {
    onChange(value);
    setShowOption(false);
  };

  const handleOutsideClick = ({ target }: MouseEvent): void => {
    if (ref.current) {
      if (showOption && !ref.current.contains(target as Node)) {
        setShowOption(false);
      }
    }
  };

  useEffect(() => {
    if (showOption) document.addEventListener("mousedown", handleOutsideClick);
    return () => {
      document.removeEventListener("mousedown", handleOutsideClick);
    };
  });

  return (
    <div ref={ref} className={"relative text-sm"}>
      <div
        className={`flex justify-between w-full h-8 rounded cursor-pointer transition duration-100 ease-linear ${
          showOption
            ? "outline outline-jin-blue-200 border border-jin-blue-400 "
            : "border hover:border-gray-400"
        }`}
        onClick={() => setShowOption(!showOption)}
      >
        <div className={"px-4 h-full flex items-center"}>
          {value ? value.label : placeholder}
        </div>
        <div
          className={`duration-500 origin-center flex items-center p-2 ${
            showOption ? "rotate-0" : "-rotate-180"
          }`}
        >
          {ChevronDown}
        </div>
      </div>
      {showOption ? (
        <ul
          className={
            "absolute z-10 border bg-white w-full mt-1 py-1 rounded shadow-md"
          }
        >
          {options.map((option, index) => {
            return (
              <li
                className={`w-full h-8 cursor-pointer`}
                key={index}
                onClick={() => handleItemChange(option)}
              >
                <div
                  className={`px-4 h-full flex items-center mx-1 rounded ${
                    option.value === value?.value
                      ? "bg-jin-blue-400 text-white"
                      : "hover:bg-jin-blue-100 hover:text-jin-blue-400"
                  }`}
                >
                  {option.label}
                </div>
              </li>
            );
          })}
        </ul>
      ) : (
        <></>
      )}
    </div>
  );
};

export default Select;
