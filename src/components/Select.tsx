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
        className={`w-full h-8 rounded cursor-pointer transition duration-100 ease-linear ${
          showOption
            ? "outline outline-2 outline-orange-200 border border-orange-400 "
            : "border hover:border-gray-400"
        }`}
        onClick={() => setShowOption(!showOption)}
        onBlur={() => setShowOption(false)}
      >
        <div className={"px-4 h-full flex items-center"}>
          {value ? value.label : placeholder}
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
                      ? "bg-orange-400 text-white"
                      : "hover:bg-orange-100 hover:text-orange-400"
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
