import React from "react";

export type OptionItemProps = {
  value: string | number;
  label: string;
};

interface CategoryListProps {
  list: OptionItemProps[];
  value?: OptionItemProps;
  onChange: (value: OptionItemProps) => void;
  className?: string;
}

const CategoryList = ({
  list,
  value,
  onChange,
  className,
}: CategoryListProps) => {
  return (
    <section className={`flex gap-2 ${className}`}>
      {list.map((item, index) => {
        const isNotLast = index + 1 !== list.length;
        const isCurrentValue = item.value === value?.value;
        return (
          <React.Fragment key={index}>
            <button
              className={`text-[14px] ${
                isCurrentValue
                  ? "text-jin-blue-400 font-bold"
                  : "hover:text-jin-blue-400"
              }
              `}
              onClick={() => onChange(item)}
            >
              {item.label}
            </button>
            {isNotLast ? (
              <div className={"font-light text-gray-300 h-fit "}>|</div>
            ) : (
              <></>
            )}
          </React.Fragment>
        );
      })}
    </section>
  );
};

export default CategoryList;
