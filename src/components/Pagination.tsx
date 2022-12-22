import React, { useMemo } from "react";
import { start } from "repl";

interface PaginationProps {
  activePage?: number;
  totalItemsCount?: number;
  itemsCountPerPage?: number;
  pageRangeDisplayed?: number;
  onChange: (page: number) => void;
}

function range(size: number, start: number = 0): number[] {
  return Array.from({ length: size }, (v, i) => i + start);
}

const Pagination = ({
  activePage = 1,
  totalItemsCount = 1,
  itemsCountPerPage = 10,
  pageRangeDisplayed = 10,
  onChange,
}: PaginationProps) => {
  const lastPage = useMemo(
    () => Math.ceil(totalItemsCount / itemsCountPerPage),
    [totalItemsCount, itemsCountPerPage]
  );
  const startPage = useMemo(
    () => pageRangeDisplayed * Math.floor(activePage / pageRangeDisplayed),
    [pageRangeDisplayed, activePage]
  );

  const pageNum = useMemo(
    () =>
      range(
        pageRangeDisplayed > totalItemsCount / itemsCountPerPage
          ? totalItemsCount / itemsCountPerPage + 1
          : itemsCountPerPage,
        startPage + 1
      ),
    [pageRangeDisplayed, totalItemsCount, startPage, itemsCountPerPage]
  );

  const handleDoubleLeftClick = () => {
    if (activePage > 1) {
      if (activePage === startPage) {
        onChange(startPage - 1);
      } else {
        onChange(startPage + 1);
      }
    }
  };
  const handleLeftClick = () => {
    if (activePage > 1) onChange(activePage - 1);
  };
  const handleRightClick = () => {
    if (activePage < lastPage) onChange(activePage + 1);
  };
  const handleDoubleRightClick = () => {
    if (activePage < lastPage) {
      if (lastPage - activePage > pageRangeDisplayed) {
        onChange(startPage + pageRangeDisplayed);
      } else {
        onChange(lastPage);
      }
    }
  };

  return (
    <div className={"flex gap-2 justify-center items-center h-20"}>
      <ArrowButton onClick={handleDoubleLeftClick}>
        {ChevronDoubleLeft}
      </ArrowButton>
      <ArrowButton onClick={handleLeftClick}>{ChevronLeft}</ArrowButton>
      <div className={"flex gap-4 mx-4"}>
        {pageNum.map((page) => (
          <PageNumberButton
            key={page}
            isFocused={activePage === page}
            onClick={onChange}
          >
            {page}
          </PageNumberButton>
        ))}
      </div>
      <ArrowButton onClick={handleRightClick}>{ChevronRight}</ArrowButton>
      <ArrowButton onClick={handleDoubleRightClick}>
        {ChevronDoubleRight}
      </ArrowButton>
    </div>
  );
};

export default Pagination;

interface ButtonProps {
  children: React.ReactNode;
  onClick: (e: React.MouseEvent<HTMLButtonElement>) => void;
}

const PageNumberButton = ({
  children,
  isFocused,
  onClick,
}: {
  children: number;
  isFocused: boolean;
  onClick: (page: number) => void;
}) => {
  return (
    <button
      className={`${isFocused ? "text-orange-400" : ""}`}
      onClick={() => onClick(children)}
    >
      {children}
    </button>
  );
};

const ArrowButton = ({ children, onClick }: ButtonProps) => {
  return (
    <button
      onClick={onClick}
      className={"flex items-center justify-center border rounded-full p-3 h-4"}
    >
      {children}
    </button>
  );
};

const ChevronDoubleLeft = (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    viewBox="0 0 20 20"
    fill="currentColor"
    className="w-4 h-4"
  >
    <path
      fillRule="evenodd"
      d="M15.79 14.77a.75.75 0 01-1.06.02l-4.5-4.25a.75.75 0 010-1.08l4.5-4.25a.75.75 0 111.04 1.08L11.832 10l3.938 3.71a.75.75 0 01.02 1.06zm-6 0a.75.75 0 01-1.06.02l-4.5-4.25a.75.75 0 010-1.08l4.5-4.25a.75.75 0 111.04 1.08L5.832 10l3.938 3.71a.75.75 0 01.02 1.06z"
      clipRule="evenodd"
    />
  </svg>
);

const ChevronLeft = (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    viewBox="0 0 20 20"
    fill="currentColor"
    className="w-4 h-4"
  >
    <path
      fillRule="evenodd"
      d="M12.79 5.23a.75.75 0 01-.02 1.06L8.832 10l3.938 3.71a.75.75 0 11-1.04 1.08l-4.5-4.25a.75.75 0 010-1.08l4.5-4.25a.75.75 0 011.06.02z"
      clipRule="evenodd"
    />
  </svg>
);

const ChevronRight = (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    viewBox="0 0 20 20"
    fill="currentColor"
    className="w-4 h-4"
  >
    <path
      fillRule="evenodd"
      d="M7.21 14.77a.75.75 0 01.02-1.06L11.168 10 7.23 6.29a.75.75 0 111.04-1.08l4.5 4.25a.75.75 0 010 1.08l-4.5 4.25a.75.75 0 01-1.06-.02z"
      clipRule="evenodd"
    />
  </svg>
);

const ChevronDoubleRight = (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    viewBox="0 0 20 20"
    fill="currentColor"
    className="w-4 h-4"
  >
    <path
      fillRule="evenodd"
      d="M10.21 14.77a.75.75 0 01.02-1.06L14.168 10 10.23 6.29a.75.75 0 111.04-1.08l4.5 4.25a.75.75 0 010 1.08l-4.5 4.25a.75.75 0 01-1.06-.02z"
      clipRule="evenodd"
    />
    <path
      fillRule="evenodd"
      d="M4.21 14.77a.75.75 0 01.02-1.06L8.168 10 4.23 6.29a.75.75 0 111.04-1.08l4.5 4.25a.75.75 0 010 1.08l-4.5 4.25a.75.75 0 01-1.06-.02z"
      clipRule="evenodd"
    />
  </svg>
);
