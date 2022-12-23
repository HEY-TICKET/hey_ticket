import React from "react";

export type OptionItemProps = {
  value: string | number;
  label: string;
};

interface CategoryListProps {
  list: OptionItemProps[];
  value: OptionItemProps;
  onChange: (value: OptionItemProps) => void;
}

const CategoryList = ({ list, value, onChange }: CategoryListProps) => {
  return (
    <section className={"flex gap-2"}>
      {list.map((item, index) => {
        const isNotLast = index + 1 !== list.length;
        const isCurrentValue = item.value === value.value;
        return (
          <React.Fragment key={index}>
            <button
              className={`text-[14px] ${
                isCurrentValue
                  ? "text-orange-400 font-bold"
                  : "hover:text-orange-400"
              }`}
              onClick={() => onChange(item)}
            >
              {item.label}
            </button>
            {isNotLast ? (
              <div className={"font-medium text-gray-300"}>|</div>
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
