import Flag from "./Flag";
import React from "react";

export interface TagProps {
  title: string;
  className?: string;
}

export interface OpenNoticeListItemProps {
  openDate: string;
  title: string;
  registrationDate: string;
  tags?: TagProps[];
  hits: number;
  posterUrl?: string;
  id: number;
}

const OpenNoticeListItem = ({
  openDate,
  title,
  registrationDate,
  tags,
  hits,
  posterUrl,
}: OpenNoticeListItemProps) => {
  return (
    <div
      className={
        "grid grid-cols-[200px_auto_100px] gap-4 border-b py-3 px-4 h-28 mx-6"
      }
    >
      <section className={"flex flex-col gap-2 justify-center text-sm"}>
        <span>티켓 오픈일</span>
        <p className={"text-xs"}>{openDate}</p>
      </section>
      <section className={"flex flex-col justify-center gap-2"}>
        <div>
          <div className={"flex gap-2"}>
            {tags?.map((tag, index) => (
              <Flag key={index} className={tag.className}>
                {tag.title}
              </Flag>
            ))}
          </div>
          <span>{title}</span>
        </div>
        <div className={"flex gap-4 text-gray-400 text-xs"}>
          <p>등록일 {registrationDate}</p>
          <div>|</div>
          <p>조회 {hits}</p>
        </div>
      </section>
      <section>
        {posterUrl ? (
          <img src={posterUrl} alt="poster" />
        ) : (
          <div
            className={
              "w-[65px] h-full bg-gray-100 text-gray-300 flex flex-col justify-center items-center m-auto text-xs font-bold"
            }
          >
            <span>OPEN</span>
            <span>TICKET</span>
          </div>
        )}
      </section>
    </div>
  );
};

export default OpenNoticeListItem;
